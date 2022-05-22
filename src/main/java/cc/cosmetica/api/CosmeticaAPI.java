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

package cc.cosmetica.api;

import cc.cosmetica.impl.CosmeticaWebAPI;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * A general interface with the Cosmetica Web API. Methods that throw IOException typically throw it when there is an issue contacting the API server, and {@link CosmeticaAPIException} if the api server can be contacted, but returns an error.
 */
public interface CosmeticaAPI {
	//////////////////////
	//  Web-API Methods //
	//////////////////////

	/**
	 * Contacts the API version checker.
	 * @param minecraftVersion the minecraft version string.
	 * @param cosmeticaVersion the cosmetica version string.
	 * @return a message sent by the API if the cosmetica version is outdated or old enough that it may not function correctly.
	 */
	ServerResponse<Optional<String>> checkVersion(String minecraftVersion, String cosmeticaVersion);

	/**
	 * Exchanges the auth token in this API instance for a 'master' and 'limited' token, if it does not already have them stored.
	 * @param uuid the UUID of the player we are requesting to have cosmetica api access tokens for.
	 * @return whether this changed the tokens stored in the API.
	 * @throws IllegalStateException if this instance was created without an auth token (i.e directly with api tokens), as there is nothing to exchange.
	 */
	ServerResponse<Boolean> exchangeTokens(UUID uuid) throws IllegalStateException;

	/**
	 * Head on the safari to check out the lion king's new cosmetics! I mean, uh, ping this to get updates on any cosmetic changes you may have missed in the last 4 minutes from users on the server you're on, and allow other cosmetica users on the same server to receive cosmetics updates for you.<br>
	 * If you provide a timestamp of 0, the endpoint will not send any users nor notifications, but instead only respond with a timestamp to use next time. The cosmetica mod calls this endpoint with a timestamp of 0 upon first joining a server to get its initial timestamp for this server.
	 * @param serverAddress the address of the minecraft server you're on. This {@link InetSocketAddress} must have an IP and port associated.
	 * @return the updates from this endpoint.
	 * @throws IllegalArgumentException if the InetSocketAddress does not have an IP and port.
	 * @apiNote the response to this endpoint provides a timestamp to use when you next call it from the same server.
	 */
	ServerResponse<CosmeticsUpdates> everyThirtySecondsInAfricaHalfAMinutePasses(InetSocketAddress serverAddress, long timestamp) throws IllegalArgumentException;

	/**
	 * Retrieves user info from the api server via either the UUID, username, or both. UUID is used preferentially.
	 * @param uuid the uuid of the player to retrieve data of.
	 * @param username the username of the player to retrieve data of.
	 * @return a representation of the cosmetics data of the given player.
	 * @throws IllegalArgumentException if both {@code uuid} and {@code username} are null.
	 */
	ServerResponse<UserInfo> getUserInfo(@Nullable UUID uuid, @Nullable String username) throws IllegalArgumentException;

	/**
	 * Retrieves the settings of the user associated with the token and some basic data.
	 * @return the user's settings, as JSON.
	 */
	ServerResponse<UserSettings> getUserSettings();

	/**
	 * Sends a version check request to the cosmetica servers and retrieves text to give to the user if there is an update, otherwise returns an empty string.
	 * @param modVersion the version of the cosmetica mod.
	 * @param minecraftVersion the minecraft version, duh. {@code (Use SharedConstants.getCurrentVersion().getId()} if you're a minecraft mod using this API).
	 * @return the text to display.
	 */
	ServerResponse<String> versionCheck(String modVersion, String minecraftVersion);

	/**
	 * Gets a page of 16 cosmetics, sorted by upload date.
	 * @param type the type of cosmetic to search for.
	 * @param page the page number to browse.
	 * @return a page of cosmetics.
	 */
	default <T extends CustomCosmetic> ServerResponse<CosmeticsPage<T>> getRecentCosmetics(CosmeticType<T> type, int page) {
		return getRecentCosmetics(type, page, 16, Optional.empty());
	}

	/**
	 * Gets a page of cosmetics that match the given query, sorted by upload date.
	 * @param type the type of cosmetic to search for.
	 * @param page the page number to browse.
	 * @param pageSize how large each page should be. For example, the desktop website uses 16, whereas mobile uses 8.
	 * @param query the search term. If a query is provided, 'official' cosmetica cosmetics may be returned in addition to user-uploaded cosmetics.
	 * @return a page of cosmetics sorted by upload date.
	 */
	<T extends CustomCosmetic> ServerResponse<CosmeticsPage<T>> getRecentCosmetics(CosmeticType<T> type, int page, int pageSize, Optional<String> query);

	/**
	 * Gets a page of 16 cosmetics sorted by popularity.
	 * @param page the page number to browse.
	 * @return a page of cosmetics sorted by popularity.
	 */
	default ServerResponse<CosmeticsPage<CustomCosmetic>> getPopularCosmetics(int page) {
		return getPopularCosmetics(page, 16);
	}

	/**
	 * Gets a page of official ("system") cosmetics.
	 * @param page the page number to browse.
	 * @param pageSize how large each page should be. For example, the desktop website uses 16, whereas mobile uses 8.
	 * @return a page of official cosmetics.
	 */
	ServerResponse<CosmeticsPage<CustomCosmetic>> getOfficialCosmetics(int page, int pageSize);

	/**
	 * Gets a page of 16 official ("system") cosmetics.
	 * @param page the page number to browse.
	 * @return a page of official cosmetics.
	 */
	default ServerResponse<CosmeticsPage<CustomCosmetic>> getOfficialCosmetics(int page) {
		return getOfficialCosmetics(page, 16);
	}

	/**
	 * Gets a page of cosmetics sorted by popularity.
	 * @param page the page number to browse.
	 * @param pageSize how large each page should be. For example, the desktop website uses 16, whereas mobile uses 8.
	 * @return a page of cosmetics sorted by popularity.
	 */
	ServerResponse<CosmeticsPage<CustomCosmetic>> getPopularCosmetics(int page, int pageSize);

	///////////////////////////
	//   Non-Web-API Methods //
	///////////////////////////

	/**
	 * Pass a consumer to be invoked with the URL whenever a URL is contacted. This can be useful for debug logging purposes.
	 * @param logger the logger to pass.
	 */
	void setUrlLogger(@Nullable Consumer<String> logger);

	/**
	 * @return whether this cosmetica api instance has an API token.
	 */
	@Nullable
	boolean hasToken();

	/**
	 * Sets a new authentication token for this instance to use. This resets the master and limited tokens stored on this instance, so {@link CosmeticaAPI#exchangeTokens(UUID)} must be called after this.
	 */
	void setAuthToken(String authenticationToken);

	/**
	 * Creates an instance with the given authentication token. This can then be exchanged with the cosmetica api for a valid new master and get token with which the cosmetica api instance will be configured.
	 * @param authenticationToken the cosmetica auth token.
	 * @return an instance of the cosmetica web api, configured with the given token.
	 * @throws IllegalStateException if an api instance cannot be retrieved.
	 */
	static CosmeticaAPI fromAuthToken(String authenticationToken) throws IllegalStateException {
		return CosmeticaWebAPI.fromAuthToken(authenticationToken);
	}

	/**
	 * @param masterToken the cosmetica master token.
	 * @return an instance of the cosmetica web api, configured with the given token.
	 *   This instance will only make requests on https, unlike other instances which make non-sensitive "get" requests under http for speed.
	 * @throws IllegalStateException if an api instance cannot be retrieved.
	 */
	static CosmeticaAPI fromToken(String masterToken) throws IllegalStateException {
		return CosmeticaWebAPI.fromTokens(masterToken, null);
	}

	/**
	 * @param limitedToken the cosmetica 'limited' or 'get' token, a special token for use over HTTP which only has access to specific "get" endpoints.
	 * @return an instance of the cosmetica web api, configured with the given token.
	 *   This instance will only make requests on http, so is less secure.
	 * @throws IllegalStateException if an api instance cannot be retrieved.
	 */
	static CosmeticaAPI fromLimitedToken(String limitedToken) throws IllegalStateException {
		return CosmeticaWebAPI.fromTokens(null, limitedToken);
	}

	/**
	 * @param masterToken the cosmetica master token.
	 * @param limitedToken the cosmetica 'limited' or 'get' token, a special token for use over HTTP which only has access to specific "get" endpoints.
	 * @return an instance of the cosmetica web api, configured with the given tokens.
	 * @throws IllegalStateException if an api instance cannot be retrieved.
	 */
	static CosmeticaAPI fromTokens(String masterToken, String limitedToken) throws IllegalStateException {
		return CosmeticaWebAPI.fromTokens(masterToken, limitedToken);
	}

	/**
	 * Creates a new instance which is not authenticated. The provided instance will be very limited in what endpoints it can call.
	 * @return an instance of the cosmetica web api with no associated token.
	 * @throws IllegalStateException if an api instance cannot be retrieved.
	 */
	static CosmeticaAPI newUnauthenticatedInstance() throws IllegalStateException {
		return CosmeticaWebAPI.newUnauthenticatedInstance();
	}

	/**
	 * Sets the file to cache the API endpoints to, and retrieve from in case of the Github CDN or "getapi" endpoints go offline.
	 */
	static void setAPICaches(File apiCache, File apiGetCache) {
		CosmeticaWebAPI.setAPICaches(apiCache, apiGetCache);
	}

	/**
	 * Get the message retrieved once a {@link CosmeticaAPI} instance is retrieved from {@link CosmeticaAPI#fromToken}, {@link CosmeticaAPI#fromTokens}, or {@link CosmeticaAPI#fromAuthToken}.
	 */
	@Nullable
	static String getMessage() {
		return CosmeticaWebAPI.getMessage();
	}

	/**
	 * Get the cosmetica website url, retrieved once a {@link CosmeticaAPI} instance is retrieved from {@link CosmeticaAPI#fromToken}, {@link CosmeticaAPI#fromTokens}, or {@link CosmeticaAPI#fromAuthToken}.
	 */
	@Nullable
	static String getWebsite() {
		return CosmeticaWebAPI.getWebsite();
	}

	/**
	 * Get the cosmetica api server url being used, retrieved once a {@link CosmeticaAPI} instance is retrieved from {@link CosmeticaAPI#fromToken}, {@link CosmeticaAPI#fromTokens}, or {@link CosmeticaAPI#fromAuthToken}.
	 */
	@Nullable
	static String getAPIServer() {
		return CosmeticaWebAPI.getApiServerHost(false);
	}

	/**
	 * Get the cosmetica api server url being used as an insecure http:// url, retrieved once a {@link CosmeticaAPI} instance is retrieved from {@link CosmeticaAPI#fromToken}, {@link CosmeticaAPI#fromTokens}, or {@link CosmeticaAPI#fromAuthToken}.
	 */
	@Nullable
	static String getHttpAPIServer() {
		return CosmeticaWebAPI.getFastInsecureApiServerHost(false);
	}
}
