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

import cc.cosmetica.impl.DummyUserSettings;

import java.util.Map;
import java.util.UUID;

/**
 * An object containing a user's settings and preferences.
 */
public interface UserSettings {
	/**
	 * @return the UTC timestamp at which this user joined.
	 */
	long getJoinTime();

	/**
	 * @return the role of the user on the Cosmetica platform. For example, "admin" or "default".
	 */
	String getRole();

	/**
	 * @return the player's UUID.
	 */
	UUID getUUID();

	/**
	 * @return whether this user sees other player's hats. If this is disabled, others will likewise not be able to see this user's hats.
	 */
	boolean doHats();

	/**
	 * @return whether this user sees other player's shoulder buddies. If this is disabled, others will likewise not be able to see this user's shoulder buddies.
	 */
	boolean doShoulderBuddies();

	/**
	 * @return whether this user sees other player's bach blings. If this is disabled, others will likewise not be able to see this user's back bling.
	 */
	boolean doBackBlings();

	/**
	 * @return whether this user sees other player's lore. If this is disabled, others will likewise not be able to see this user's lore.
	 */
	boolean doLore();

	/**
	 * Gets whether the user has online activity enabled.
	 * @return whether the user has online activity enabled. If this is on, their online status will be accurate. Otherwise, they will always appear online.
	 */
	boolean doOnlineActivity();

	/**
	 * Gets the icon settings of the user.
	 * @return the icon settings of the user, as a packed integer. The individual flags are <ul>
	 *     <li>{@link UserSettings#DISABLE_ICONS}</li>
	 *     <li>{@link UserSettings#DISABLE_OFFLINE_ICONS}</li>
	 *     <li>{@link UserSettings#DISABLE_SPECIAL_ICONS}</li>
	 * </ul>
	 */
	int getIconSettings();

	/**
	 * @return the country code of this user.
	 * @implNote Other users cannot see this user's country code. It is stored in any case so the servers can update the data they send immediately as soon as a user wishes to enable per-region effects.
	 */
	String getCountryCode();

	/**
	 * @return whether per-region effects is enabled for this user. If they have it disabled, they will not be able to see the per-region effects of other users either.
	 */
	boolean hasPerRegionEffects();

	/**
	 * Whether the user has the per-region-effects field set. If it is not set, the server will treat it as disabled, and the mod will prompt the player to choose whether to enable or disable it.
	 * @return whether this user has per-region effects set.
	 */
	boolean hasPerRegionEffectsSet();

	/**
	 * @return the id of the panorama this user uses on the website.
	 */
	int getPanorama();

	/**
	 * @return this user's cape server settings.
	 */
	Map<String, CapeServer> getCapeServerSettings();

	/**
	 * A {@link UserSettings} instance with dummy values for everything which can be used as a placeholder.
	 * @implNote These dummy user settings have an empty map for cape server settings and a country code of AQ (antarctica).
	 */
	UserSettings DUMMY = new DummyUserSettings();

	// icon flags

	/**
	 * Flag for whether to disable all icons, regardless of other flags.
	 */
	int DISABLE_ICONS = 0x1;

	/**
	 * Flag for whether to disable offline icons. When this flag is enabled, offline users will not have an icon sent.
	 */
	int DISABLE_OFFLINE_ICONS = 0x2;

	/**
	 * Flag for whether to disable "special" icons. Special icons include any icons other than the default Cosmetica icon: Cosmetica++ icons and affiliated third party icons.
	 * When this flag is enabled, the default Cosmetica icon will be sent instead of these special icons.
	 */
	int DISABLE_SPECIAL_ICONS = 0x4;
}
