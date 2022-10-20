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

/**
 * Class containing the settings for icon display for the given user. These settings control how the user
 * sees the icons of themself and others, not how others see them. The class data is mutable.
 */
public final class IconSettings {

	public IconSettings() {
	}

	private boolean disableIcons;
	private boolean disableOfflineIcons;
	private boolean disableAffiliatedClientIcons;
	private boolean disableThirdPartyCapeServerIcons;

	// === BUILDER-STYLE SETTER === //

	/**
	 * Sets whether to disable icons entirely for this user.
	 * @param disableIcons whether to disable icons entirely for this user.
	 * @return this.
	 */
	public IconSettings disableIcons(boolean disableIcons){
		this.disableIcons = disableIcons;
		return this;
	}

	/**
	 * Sets whether icons of offline players should not be shown to the user.
	 * @param disableOfflineIcons whether icons of offline players should not be shown to the player.
	 * @return this.
	 */
	public IconSettings disableOfflineIcons(boolean disableOfflineIcons) {
		this.disableOfflineIcons = disableOfflineIcons;
		return this;
	}

	/**
	 * Sets whether icons of affiliated clients (i.e. icons from clients other than the cosmetica mod itself) should be hidden to the user.
	 * @param disableAffiliatedClientIcons whether icons of affiliated clients (i.e. icons from clients other than the cosmetica mod itself) should be hidden to the user.
	 * @return this.
	 */
	public IconSettings disableAffiliatedClientIcons(boolean disableAffiliatedClientIcons) {
		this.disableAffiliatedClientIcons = disableAffiliatedClientIcons;
		return this;
	}

	/**
	 * Sets whether icons of third party cape servers should be hidden to the user.
	 * @param disableThirdPartyCapeServerIcons whether icons of third party cape servers should be hidden to the user.
	 * @return this.
	 */
	public IconSettings disableThirdPartyCapeServerIcons(boolean disableThirdPartyCapeServerIcons) {
		this.disableThirdPartyCapeServerIcons = disableThirdPartyCapeServerIcons;
		return this;
	}

	// === GETTER === //

	/**
	 * @return whether to disable icons entirely for this user.
	 */
	public boolean disableIcons() {
		return disableIcons;
	}

	/**
	 * @return whether icons of offline players should not be shown to the player.
	 */
	public boolean disableOfflineIcons() {
		return disableOfflineIcons;
	}

	/**
	 * @return whether icons of affiliated clients (i.e. icons from clients other than the cosmetica mod itself) should be hidden to the user.
	 */
	public boolean disableAffiliatedClientIcons() {
		return disableAffiliatedClientIcons;
	}

	/**
	 * @return whether icons of third party cape servers should be hidden to the user.
	 */
	public boolean disableThirdPartyCapeServerIcons() {
		return disableThirdPartyCapeServerIcons;
	}

	// === PACKED INTEGER REPRESENTATION === //

	/**
	 * Packs the data in this {@link IconSettings} instance into a single integer for sending to the server.
	 * @return the packed integer containing the flags values in this class.
	 */
	public int packToInt() {
		return (this.disableIcons							? DISABLE_ICONS							: 0)
				| (this.disableOfflineIcons					? DISABLE_OFFLINE_ICONS					: 0)
				| (this.disableAffiliatedClientIcons		? DISABLE_AFFILIATED_CLIENT_ICONS		: 0)
				| (this.disableThirdPartyCapeServerIcons	? DISABLE_THIRD_PARTY_CAPE_SERVER_ICONS	: 0);
	}

	public static final int DISABLE_ICONS							= 0x1;
	public static final int DISABLE_OFFLINE_ICONS					= 0x2;
	public static final int DISABLE_AFFILIATED_CLIENT_ICONS			= 0x4;
	public static final int DISABLE_THIRD_PARTY_CAPE_SERVER_ICONS	= 0x8;
}
