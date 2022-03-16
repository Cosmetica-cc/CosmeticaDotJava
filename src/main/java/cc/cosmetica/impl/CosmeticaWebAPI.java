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
import cc.cosmetica.api.UserInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class CosmeticaWebAPI implements CosmeticaAPI {
	private CosmeticaWebAPI(String masterToken, @Nullable String getToken) {
		this.masterToken = masterToken;
		this.limitedToken = getToken;
	}

	private CosmeticaWebAPI(String authenticationToken) {
		this.masterToken = "";
		this.limitedToken = "";
		this.authToken = authenticationToken;
	}

	@Nullable private String masterToken;
	@Nullable private String limitedToken;
	@Nullable private String authToken;
	private Consumer<String> urlLogger = s -> {};

	@Override
	public Optional<String> checkVersion(String minecraftVersion, String cosmeticaVersion) throws IOException {
		String versionCheck = apiServerHost + "/get/versioncheck?modversion="
				+ Util.urlEncode(cosmeticaVersion)
				+ "&mcversion=" + Util.urlEncode(minecraftVersion);

		this.urlLogger.accept(versionCheck);

		try (Response response = Response.request(versionCheck)) {
			String s = response.getAsString();

			return s.isEmpty() ? Optional.empty() : Optional.of(s);
		}
	}

	@Override
	public boolean exchangeTokens(UUID uuid, String accessToken) throws IllegalStateException, IOException, CosmeticaAPIException {
		if (this.authToken == null) throw new IllegalStateException("This instance does not have a stored auth token! Perhaps it was created directly with API tokens.");
		if (this.masterToken != null) return false;

		try (Response response = Response.request(apiServerHost + "/client/verifyforauthtokens?token=" + this.authToken + "&uuid=" + uuid + "&access-token=" + accessToken)) {
			JsonObject object = response.getAsJson();

			if (object.has("error")) {
				throw new CosmeticaAPIException("Error exchanging tokens!"); // TODO do we send a message here?
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
	public void setUrlLogger(Consumer<String> urlLogger) {
		this.urlLogger = urlLogger;
	}

	private String createLimitedGet(String target) {
		if (this.limitedToken != null) return fastInsecureApiServerHost + target + "&token=" + this.limitedToken + "&timestamp=" + System.currentTimeMillis();
		else return createGet(target);
	}

	private String createGet(String target) {
		if (this.masterToken != null) return apiServerHost + target + "&token=" + this.masterToken + "&timestamp=" + System.currentTimeMillis();
		else return apiServerHost + target + "&token=&timestamp=" + System.currentTimeMillis();
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

	public static String getApiServer() {
		return apiServerHost;
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
