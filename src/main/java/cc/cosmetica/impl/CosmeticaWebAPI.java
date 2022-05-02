/**
 * Copyright (c) 2022 EyezahMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.cosmetica.impl;

import cc.cosmetica.api.CosmeticaAPI;
import cc.cosmetica.api.CosmeticaAPIException;
import cc.cosmetica.api.CosmeticsUpdates;
import cc.cosmetica.api.ServerResponse;
import cc.cosmetica.api.User;
import cc.cosmetica.api.UserInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.function.Consumer;

public class CosmeticaWebAPI implements CosmeticaAPI {
	private CosmeticaWebAPI(String masterToken, @Nullable String getToken) {
		this.masterToken = masterToken;
		this.limitedToken = getToken;
	}

	private CosmeticaWebAPI(String authenticationToken) {
		this.masterToken = null;
		this.limitedToken = null;
		this.authToken = authenticationToken;
	}

	@Nullable private String masterToken;
	@Nullable private String limitedToken;
	@Nullable private String authToken;
	private Consumer<String> urlLogger = s -> {};

	@Override
	public ServerResponse<Optional<String>> checkVersion(String minecraftVersion, String cosmeticaVersion) {
		String versionCheck = apiServerHost + "/get/versioncheck?modversion="
				+ Util.urlEncode(cosmeticaVersion)
				+ "&mcversion=" + Util.urlEncode(minecraftVersion);

		this.urlLogger.accept(versionCheck);

		try (Response response = Response.requestAndVerify(versionCheck)) {
			String s = response.getAsString();
			return new ServerResponse<>(s.isEmpty() ? Optional.empty() : Optional.of(s));
		} catch (Exception e) {
			return new ServerResponse<>(e);
		}
	}

	@Override
	public boolean exchangeTokens(UUID uuid, String accessToken) throws IllegalStateException, IOException, CosmeticaAPIException {
		if (this.authToken == null) throw new IllegalStateException("This instance does not have a stored auth token! Perhaps it was created directly with API tokens.");
		if (this.masterToken != null) return false;

		try (Response response = Response.request(apiServerHost + "/client/verifyforauthtokens?token=" + this.authToken + "&uuid=" + uuid + "&access-token=" + accessToken)) {
			JsonObject object = response.getAsJson();

			if (object.has("error")) {
				throw new CosmeticaAPIException("Error exchanging tokens! " + object.get("error").getAsString());
			}

			this.masterToken = object.get("master_token").getAsString();
			this.limitedToken = object.get("limited_token").getAsString();

			return true;
		}
	}

	@Override
	public UserInfo getUserInfo(@Nullable UUID uuid, @Nullable String username) throws IOException, IllegalArgumentException {
		String target = createLimitedGet("/get/info?username=" + Util.urlEncode(username) + "&uuid=" + uuid);
		this.urlLogger.accept(target);

		try (Response response = Response.request(target)) {
			JsonObject jsonObject = response.getAsJson();
			JsonObject hat = jsonObject.has("hat") ? jsonObject.get("hat").getAsJsonObject() : null;
			JsonObject shoulderBuddy = jsonObject.has("shoulder-buddy") ? jsonObject.get("shoulder-buddy").getAsJsonObject() : null;
			JsonObject cloak = jsonObject.has("cape") ? jsonObject.get("cape").getAsJsonObject() : null;

			return new UserInfo(
					jsonObject.get("lore").getAsString(),
					jsonObject.get("upside-down").getAsBoolean(),
					jsonObject.get("prefix").getAsString(),
					jsonObject.get("suffix").getAsString(),
					BaseModel.parse(hat),
					BaseModel.parse(shoulderBuddy),
					BaseCape.parse(cloak)
			);
		}
	}

	@Override
	public CosmeticsUpdates everyThirtySecondsInAfricaHalfAMinutePasses(InetSocketAddress serverAddress, long timestamp) throws IOException, CosmeticaAPIException, IllegalArgumentException {
		String awimbawe = createGet("/get/everythirtysecondsinafricahalfaminutepasses?ip=" + Util.base64Ip(serverAddress), OptionalLong.of(timestamp));

		this.urlLogger.accept(awimbawe);

		try (Response theLionSleepsTonight = Response.request(awimbawe)) {
			JsonObject theMightyJungle = theLionSleepsTonight.getAsJson();

			if (theMightyJungle.has("error")) {
				throw new CosmeticaAPIException("Server responded with error while checking for cosmetic updates : " + theMightyJungle.get("error").getAsString());
			}

			List<String> notifications = List.of();

			if (theMightyJungle.has("notifications")) {
				JsonArray jNotif = theMightyJungle.get("notifications").getAsJsonArray();
				notifications = new ArrayList<>(jNotif.size());

				for (JsonElement elem : jNotif) {
					notifications.add(elem.getAsString());
				}
			}

			JsonObject updates = theMightyJungle.get("updates").getAsJsonObject();

			List<User> users = List.of();

			if (updates.has("list")) {
				JsonArray jUpdates = updates.getAsJsonArray("list");
				users = new ArrayList<>(jUpdates.size());

				for (JsonElement element : jUpdates) {
					JsonObject individual = element.getAsJsonObject();

					UUID uuid = UUID.fromString(Util.dashifyUUID(individual.get("uuid").getAsString()));

					users.add(new User(uuid, individual.get("username").getAsString()));
				}
			}

			return new CosmeticsUpdates(notifications, users, updates.get("timestamp").getAsLong());
		}
	}

	private String createLimitedGet(String target) {
		if (this.limitedToken != null) return fastInsecureApiServerHost + target + "&token=" + this.limitedToken + "&timestamp=" + System.currentTimeMillis();
		else return createGet(target, OptionalLong.empty());
	}

	private String createGet(String target, OptionalLong timestamp) {
		if (this.masterToken != null) return apiServerHost + target + "&token=" + this.masterToken + "&timestamp=" + timestamp.orElseGet(System::currentTimeMillis);
		else return apiServerHost + target + "&token=&timestamp=" + timestamp.orElseGet(System::currentTimeMillis);
	}

	@Override
	public void setUrlLogger(Consumer<String> urlLogger) {
		this.urlLogger = urlLogger;
	}

	@Override
	public @Nullable boolean hasToken() {
		return this.masterToken != null;
	}

	@Override
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
		this.masterToken = null;
		this.limitedToken = null;
	}

	private static String apiServerHost;
	private static String fastInsecureApiServerHost;

	private static String websiteHost;
	private static String authServerHost;

	private static String message;

	private static File apiCache;
	private static File apiGetCache;

	public static String getMessage() {
		return message;
	}

	public static String getWebsite() {
		return websiteHost;
	}

	public static CosmeticaAPI fromAuthToken(String authenticationToken) throws IllegalStateException {
		retrieveAPIIfNoneCached();
		return new CosmeticaWebAPI(authenticationToken);
	}

	public static CosmeticaAPI fromTokens(String masterToken, @Nullable String getToken) throws IllegalStateException {
		retrieveAPIIfNoneCached();
		return new CosmeticaWebAPI(masterToken, getToken);
	}

	public static CosmeticaAPI newUnauthenticatedInstance() throws IllegalStateException {
		retrieveAPIIfNoneCached();
		return new CosmeticaWebAPI(null, null);
	}

	@Nullable
	public static String getApiServerHost(boolean requireResult) throws IllegalStateException {
		if (requireResult) retrieveAPIIfNoneCached();
		return apiServerHost;
	}

	@Nullable
	public static String getFastInsecureApiServerHost(boolean requireResult) throws IllegalStateException {
		if (requireResult) retrieveAPIIfNoneCached();
		return fastInsecureApiServerHost;
	}

	public static void setAPICaches(File api, File apiGet) {
		apiCache = api;
		apiGetCache = apiGet;
	}

	private static void retrieveAPIIfNoneCached() throws IllegalStateException {
		if (apiCache == null) {
			String apiGetHost = null;

			try (Response response = Response.request("https://raw.githubusercontent.com/EyezahMC/Cosmetica/master/api_provider_host.json?timestamp=" + System.currentTimeMillis())) {
				if (response.getError().isEmpty()) {
					apiGetHost = response.getAsJson().get("current_host").getAsString();
				}
			} catch (Exception e) {
				e.printStackTrace(); // this is probably bad practise?
			}

			if (apiGetCache != null) apiGetHost = Util.loadOrCache(apiGetCache, apiGetHost);
			if (apiGetHost == null) apiGetHost = "https://cosmetica.cc/getapi"; // fallback

			String apiGetData = null;

			try (Response apiGetResponse = Response.request(apiGetHost)) {
				apiGetData = apiGetResponse.getAsString();
			} catch (Exception e) {
				System.err.println("(Cosmetica API) Connection error to API GET. Trying to retrieve from local cache...");
			}

			if (apiCache != null) apiGetData = Util.loadOrCache(apiCache, apiGetData);

			if (apiGetData == null) {
				throw new IllegalStateException("Could not receive Cosmetica API host. Mod functionality will be disabled!");
			}

			JsonObject data = JsonParser.parseString(apiGetData).getAsJsonObject();
			apiServerHost = data.get("api").getAsString();
			fastInsecureApiServerHost = "http" + apiServerHost.substring(5);
			websiteHost = data.get("website").getAsString();
			JsonObject auth = data.get("auth-server").getAsJsonObject();
			authServerHost = auth.get("hostname").getAsString() + ":" + auth.get("port").getAsInt();
			message = data.get("message").getAsString();
		}
	}
}
