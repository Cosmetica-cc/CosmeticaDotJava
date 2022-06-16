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

import cc.cosmetica.api.*;
import cc.cosmetica.util.Response;
import cc.cosmetica.util.SafeURL;
import cc.cosmetica.util.Yootil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
		SafeURL versionCheck = createTokenless("/get/versioncheck?modversion="
				+ Yootil.urlEncode(cosmeticaVersion)
				+ "&mcversion=" + Yootil.urlEncode(minecraftVersion), OptionalLong.empty());

		this.urlLogger.accept(versionCheck.safeUrl());

		try (Response response = Response.get(versionCheck)) {
			String s = response.getAsString();
			return new ServerResponse<>(s.isEmpty() ? Optional.empty() : Optional.of(s), versionCheck);
		} catch (Exception e) {
			return new ServerResponse<>(e, versionCheck);
		}
	}

	@Override
	public ServerResponse<LoginInfo> exchangeTokens(UUID uuid) throws IllegalStateException {
		if (this.authToken == null) throw new IllegalStateException("This instance does not have a stored auth token! Perhaps it was created directly with API tokens.");

		SafeURL url = SafeURL.of(apiServerHost + "/client/verifyforauthtokens?uuid=" + uuid, this.authToken);

		try (Response response = Response.get(url)) {
			JsonObject object = response.getAsJson();

			if (object.has("error")) {
				throw new CosmeticaAPIException("Error exchanging tokens! " + object.get("error").getAsString());
			}

			this.masterToken = object.get("master_token").getAsString();
			this.limitedToken = object.get("limited_token").getAsString();

			return new ServerResponse<>(new LoginInfo(object.get("is_new_player").getAsBoolean(), object.has("has_special_cape") ? object.get("has_special_cape").getAsBoolean() : false), url);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, url);
		}
	}

	@Override
	public ServerResponse<UserInfo> getUserInfo(@Nullable UUID uuid, @Nullable String username) throws IllegalArgumentException {
		if (uuid == null && username == null) throw new IllegalArgumentException("Both uuid and username are null!");

		SafeURL target = createLimited("/v2/get/info?username=" + Yootil.urlEncode(username) + "&uuid=" + Yootil.urlEncode(uuid));
		this.urlLogger.accept(target.safeUrl());

		try (Response response = Response.get(target)) {
			JsonObject jsonObject = response.getAsJson();
			checkErrors(target, jsonObject);

			JsonArray hats = jsonObject.has("hats") ? jsonObject.get("hats").getAsJsonArray() : null;
			JsonObject shoulderBuddy = jsonObject.has("shoulderBuddy") ? jsonObject.get("shoulderBuddy").getAsJsonObject() : null;
			JsonObject backBling = jsonObject.has("backBling") ? jsonObject.get("backBling").getAsJsonObject() : null;
			JsonObject cloak = jsonObject.has("cape") ? jsonObject.get("cape").getAsJsonObject() : null;

			return new ServerResponse<>(new UserInfo(
					jsonObject.get("lore").getAsString(),
					jsonObject.get("upsideDown").getAsBoolean(),
					jsonObject.get("prefix").getAsString(),
					jsonObject.get("suffix").getAsString(),
					Yootil.mapObjects(hats, ModelImpl::_parse),
					ModelImpl.parse(shoulderBuddy),
					ModelImpl.parse(backBling),
					BaseCape.parse(cloak)
			), target);
		} catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	@Override
	public ServerResponse<UserSettings> getUserSettings() {
		SafeURL target = createLimited("/v2/get/settings");
		this.urlLogger.accept(target.safeUrl());

		try (Response response = Response.get(target)) {
			JsonObject data = response.getAsJson();
			checkErrors(target, data);

			JsonObject capeSettings = data.get("capeSettings").getAsJsonObject();
			Map<String, CapeServer> oCapeServerSettings = new HashMap<>();

			for (String key : capeSettings.keySet()) {
				JsonObject setting = capeSettings.get(key).getAsJsonObject();

				oCapeServerSettings.put(key, new CapeServer(
						setting.get("name").getAsString(),
						setting.get("warning").getAsString(),
						setting.get("checkOrder").getAsInt(),
						CapeDisplay.byId(setting.get("setting").getAsInt())
				));
			}

			return new ServerResponse<>(new UserSettings(
					UUID.fromString(Yootil.dashifyUUID(data.get("uuid").getAsString())),
					// cosmetics
					data.get("cape").getAsString(),
					data.get("hat").getAsString(),
					data.get("doHats").getAsBoolean(),
					data.get("shoulderBuddy").getAsString(),
					data.get("doShoulderBuddies").getAsBoolean(),
					data.get("lore").getAsString(),
					data.get("doLore").getAsBoolean(),
					// other stuff
					data.get("joined").getAsLong(),
					data.get("role").getAsString(),
					data.get("countryCode").getAsString(),
					data.get("perRegionEffects").getAsBoolean(),
					data.get("perRegionEffectsSet").getAsBoolean(),
					data.get("panorama").getAsInt(),
					oCapeServerSettings
			), target);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	/**
	 * Generics hack.
	 * @param <T> the class to force it to reference through generics so the darn thing compiles.
	 */
	private static class GeneralCosmeticType<T extends CustomCosmetic> {
		private static <T extends CustomCosmetic> GeneralCosmeticType<T> from(CosmeticType<T> type) {
			return new GeneralCosmeticType<>();
		}

		private static GeneralCosmeticType<CustomCosmetic> any() {
			return new GeneralCosmeticType<>();
		}
	}

	private <T extends CustomCosmetic> ServerResponse<CosmeticsPage<T>> getCosmeticsPage(SafeURL url, GeneralCosmeticType<T> cosmeticType) {
		this.urlLogger.accept(url.safeUrl());

		try (Response response = Response.get(url)) {
			JsonObject json = response.getAsJson();
			checkErrors(url, json);

			boolean nextPage = json.get("nextPage").getAsBoolean();
			List<T> cosmetics = new ArrayList<>();

			for (JsonElement element : json.getAsJsonArray("list")) {
				cosmetics.add((T) parse(element.getAsJsonObject()));
			}

			return new ServerResponse<>(new CosmeticsPage<>(cosmetics, nextPage), url);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, url);
		}
	}

	@Override
	public <T extends CustomCosmetic> ServerResponse<CosmeticsPage<T>> getRecentCosmetics(CosmeticType<T> type, int page, int pageSize, Optional<String> query) {
		SafeURL url = createTokenless("/get/recentcosmetics?type=" + type.urlstring + "&page=" + page + "&pagesize=" + pageSize + "&query=" + Yootil.base64(query.orElse("")), OptionalLong.empty());
		return getCosmeticsPage(url, GeneralCosmeticType.from(type));
	}

	@Override
	public ServerResponse<CosmeticsPage<CustomCosmetic>> getPopularCosmetics(int page, int pageSize) {
		SafeURL url = createTokenless("/get/popularcosmetics?page=" + page + "&pagesize=" + pageSize, OptionalLong.empty());
		return getCosmeticsPage(url, GeneralCosmeticType.any());
	}

	@Override
	public ServerResponse<CosmeticsPage<CustomCosmetic>> getOfficialCosmetics(int page, int pageSize) {
		SafeURL url = createTokenless("/get/systemcosmetics?page=" + page + "&pagesize=" + pageSize, OptionalLong.empty());
		return getCosmeticsPage(url, GeneralCosmeticType.any());
	}

	@Override
	public ServerResponse<List<String>> getLoreList(LoreType type) throws IllegalArgumentException {
		if (type == LoreType.DISCORD || type == LoreType.TWITCH) throw new IllegalArgumentException("Invalid lore type for getLoreList: " + type);

		SafeURL url = createLimited("/get/lorelists?type=" + type.toString().toLowerCase(Locale.ROOT));
		this.urlLogger.accept(url.safeUrl());

		try (Response response = Response.get(url)) {
			return new ServerResponse<>(Yootil.toStringList(getAsArray(url, response.getAsJsonElement())), url);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, url);
		}
	}

	@Override
	public <T extends CustomCosmetic> ServerResponse<T> getCosmetic(CosmeticType<T> type, String id) {
		SafeURL url = createTokenless("/get/cosmetic?type=" + type.urlstring + "&id=" + id, OptionalLong.empty());
		this.urlLogger.accept(url.safeUrl());

		try (Response response = Response.get(url)) {
			JsonObject json = response.getAsJson();
			checkErrors(url, json);

			return new ServerResponse<>((T) parse(json), url);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, url);
		}
	}

	@Override
	public ServerResponse<List<Panorama>> getPanoramas() {
		SafeURL url = createLimited("/get/panoramas");
		this.urlLogger.accept(url.safeUrl());

		try (Response response = Response.get(url)) {
			JsonArray json = getAsArray(url, response.getAsJsonElement());
			List<Panorama> result = new ArrayList<>();

			for (JsonElement element : json) {
				JsonObject pano = element.getAsJsonObject();
				result.add(new Panorama(pano.get("id").getAsInt(), pano.get("name").getAsString(), pano.get("free").getAsBoolean()));
			}

			return new ServerResponse<>(result, url);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, url);
		}
	}

	@Override
	public ServerResponse<CosmeticsUpdates> everyThirtySecondsInAfricaHalfAMinutePasses(InetSocketAddress serverAddress, long timestamp) throws IllegalArgumentException {
		SafeURL awimbawe = create("/get/everythirtysecondsinafricahalfaminutepasses?ip=" + Yootil.base64Ip(serverAddress), OptionalLong.of(timestamp));

		this.urlLogger.accept(awimbawe.safeUrl());

		try (Response theLionSleepsTonight = Response.get(awimbawe)) {
			JsonObject theMightyJungle = theLionSleepsTonight.getAsJson();
			checkErrors(awimbawe, theMightyJungle);

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

					UUID uuid = UUID.fromString(Yootil.dashifyUUID(individual.get("uuid").getAsString()));

					users.add(new User(uuid, individual.get("username").getAsString()));
				}
			}

			return new ServerResponse<>(new CosmeticsUpdates(notifications, users, updates.get("timestamp").getAsLong()), awimbawe);
		} catch (Exception e) {
			return new ServerResponse<>(e, awimbawe);
		}
	}

	// Client/ endpoints

	private ServerResponse<String> requestSet(SafeURL target) {
		this.urlLogger.accept(target.safeUrl());

		try (Response response = Response.get(target)) {
			JsonObject json = response.getAsJson();
			checkErrors(target, json);
			return new ServerResponse<>(json.get("success").getAsString(), target);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	private ServerResponse<Boolean> requestSetZ(SafeURL target) {
		this.urlLogger.accept(target.safeUrl());

		try (Response response = Response.get(target)) {
			JsonObject json = response.getAsJson();
			checkErrors(target, json);
			return new ServerResponse<>(json.get("success").getAsBoolean(), target);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	@Override
	public ServerResponse<Boolean> setCosmetic(CosmeticType<?> type, String id) {
		SafeURL target = create("/client/setcosmetic?type=" + type.urlstring + "&id=" + id, OptionalLong.empty());
		return requestSetZ(target);
	}

	@Override
	public ServerResponse<String> setLore(LoreType type, String lore) {
		if (type == LoreType.DISCORD || type == LoreType.TWITCH) throw new IllegalArgumentException("Invalid lore type for getLoreList: " + type);

		SafeURL target = create("/client/setlore?type=" + type.toString().toLowerCase(Locale.ROOT) + "&lore=" + Yootil.urlEncode(lore), OptionalLong.empty());
		return requestSet(target);
	}

	@Override
	public ServerResponse<Boolean> setPanorama(int id) {
		SafeURL target = create("/client/setpanorama?panorama=" + id, OptionalLong.empty());
		return requestSetZ(target);
	}

	@Override
	public ServerResponse<Boolean> setCapeServerSettings(Map<String, CapeDisplay> settings) {
		SafeURL target = create("/client/capesettings?" + settings.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue().toString().toLowerCase(Locale.ROOT)).collect(Collectors.joining("&")), OptionalLong.empty());
		return requestSetZ(target);
	}

	@Override
	public ServerResponse<Boolean> updateUserSettings(Map<String, Object> settings) {
		SafeURL target = create("/v2/client/updatesettings?" + settings.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&")), OptionalLong.empty());
		return requestSetZ(target);
	}

	@Override
	public ServerResponse<String> uploadCape(String name, String base64Image) {
		SafeURL target = create("/client/uploadcloak", OptionalLong.empty());

		try (Response response = Response.post(target)
				.set("name", name)
				.set("image", base64Image)
				.submit()) {
			JsonObject obj = response.getAsJson();
			checkErrors(target, obj);

			return new ServerResponse<>(obj.get("success").getAsString(), target);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	@Override
	public ServerResponse<String> uploadModel(CosmeticType<Model> type, String name, String base64Texture, JsonObject model) {
		SafeURL target = create("/client/upload" + type.urlstring, OptionalLong.empty());

		try (Response response = Response.post(target)
				.set("name", name)
				.set("image", base64Texture)
				.set("model", model.toString())
				.submit()) {
			JsonObject obj = response.getAsJson();
			checkErrors(target, obj);

			return new ServerResponse<>(obj.get("success").getAsString(), target);
		}
		catch (Exception e) {
			return new ServerResponse<>(e, target);
		}
	}

	private SafeURL createLimited(String target) {
		if (this.limitedToken != null) return SafeURL.of(fastInsecureApiServerHost + target + (target.indexOf('?') == -1 ? "?" : "&") + "timestamp=" + System.currentTimeMillis(), this.limitedToken);
		else return create(target, OptionalLong.empty());
	}

	private SafeURL create(String target, OptionalLong timestamp) {
		if (this.masterToken != null) return SafeURL.of(apiServerHost + target + (target.indexOf('?') == -1 ? "?" : "&") + "timestamp=" + timestamp.orElseGet(System::currentTimeMillis), this.masterToken);
		else return SafeURL.of(apiServerHost + target + (target.indexOf('?') == -1 ? "?" : "&") + "timestamp=" + timestamp.orElseGet(System::currentTimeMillis));
	}

	private SafeURL createTokenless(String target, OptionalLong timestamp) {
		return SafeURL.of(apiServerHost + target + (target.indexOf('?') == -1 ? "?" : "&") + "timestamp=" + timestamp.orElseGet(System::currentTimeMillis));
	}

	@Override
	public void setUrlLogger(Consumer<String> urlLogger) {
		this.urlLogger = urlLogger;
	}

	@Override
	public boolean isFullyAuthenticated() {
		return this.masterToken != null;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isFullyAuthenticated() || this.limitedToken != null;
	}

	@Override
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
		this.masterToken = null;
		this.limitedToken = null;
	}

	/**
	 * Use this method if you're cringe.<br/>
	 * (exists to stop reflection being necessary for the few times it's justified to manually get the token rather than going through the api)
	 * @return the master token on this instance
	 */
	public String getMasterToken() {
		return this.masterToken;
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

	public static CosmeticaAPI fromTokens(String masterToken, @Nullable String limitedToken) throws IllegalStateException {
		retrieveAPIIfNoneCached();
		return new CosmeticaWebAPI(masterToken, limitedToken);
	}

	public static CosmeticaAPI newUnauthenticatedInstance() throws IllegalStateException {
		retrieveAPIIfNoneCached();
		return new CosmeticaWebAPI(null, (String) null);
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

	@Nullable
	public static String getAuthServerHost(boolean requireResult) throws IllegalStateException {
		if (requireResult) retrieveAPIIfNoneCached();
		return authServerHost;
	}

	public static void setAPICaches(File api, File apiGet) {
		apiCache = api;
		apiGetCache = apiGet;
	}

	private static void retrieveAPIIfNoneCached() throws IllegalStateException {
		if (apiServerHost == null) { // if this sequence has not already been initiated
			String apiGetHost = null;

			try (Response response = Response.get("https://raw.githubusercontent.com/EyezahMC/Cosmetica/master/api_provider_host.json?timestamp=" + System.currentTimeMillis())) {
				if (response.getError().isEmpty()) {
					apiGetHost = response.getAsJson().get("current_host").getAsString();
				}
			} catch (Exception e) {
				e.printStackTrace(); // this is probably bad practise?
			}

			if (apiGetCache != null) apiGetHost = Yootil.loadOrCache(apiGetCache, apiGetHost);
			if (apiGetHost == null) apiGetHost = "https://cosmetica.cc/getapi"; // fallback

			String apiGetData = null;

			try (Response apiGetResponse = Response.get(apiGetHost)) {
				apiGetData = apiGetResponse.getAsString();
			} catch (Exception e) {
				System.err.println("(Cosmetica API) Connection error to API GET. Trying to retrieve from local cache...");
			}

			if (apiCache != null) apiGetData = Yootil.loadOrCache(apiCache, apiGetData);

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

	private static void checkErrors(SafeURL url, JsonObject response) {
		if (response.has("error")) {
			throw new CosmeticaAPIException("API server request to " + url.safeUrl() + " responded with error: " + response.get("error").getAsString());
		}
	}

	private static CustomCosmetic parse(JsonObject object) {
		// yes this code is (marginally) better
		// no I will not use it instead of the above
		CosmeticType<?> type = CosmeticType.fromTypeString(object.get("type").getAsString()).get();

		if (type == CosmeticType.CAPE) {
			return (CustomCosmetic) BaseCape.parse(object).get();
		}
		else {
			return ModelImpl.parse(object).get();
		}
	}

	private static JsonArray getAsArray(SafeURL url, JsonElement element) throws CosmeticaAPIException {
		if (element.isJsonObject()) {
			checkErrors(url, element.getAsJsonObject());
		}

		return element.getAsJsonArray();
	}
}
