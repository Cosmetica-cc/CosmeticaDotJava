/*
 * Copyright 2022, 2023 EyezahMC
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

import cc.cosmetica.api.settings.CapeServer;
import cc.cosmetica.api.settings.UserSettings;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * An object containing a user's settings and preferences.
 */
public final class UserSettingsImpl implements UserSettings {
	public UserSettingsImpl(UUID uuid, boolean doHats, boolean doShoulderBuddies, boolean doBackBlings, boolean doLore, int iconSettings,
							long joined, String role, String countryCode, boolean perRegionEffects, boolean perRegionEffectsSet, int panorama,
							boolean doOnlineActivity, Map<String, CapeServer> capeServerSettings) {
		this.uuid = uuid;
		this.doHats = doHats;
		this.doShoulderBuddies = doShoulderBuddies;
		this.doBackBlings = doBackBlings;
		this.doLore = doLore;
		this.iconSettings = iconSettings;
		this.joined = joined;
		this.role = role;
		this.countryCode = countryCode;
		this.perRegionEffects = perRegionEffects;
		this.perRegionEffectsSet = perRegionEffectsSet;
		this.panorama = panorama;
		this.onlineActivity = doOnlineActivity;
		this.capeServerSettings = capeServerSettings;
	}

	private final UUID uuid;
	private final boolean doHats;
	private final boolean doShoulderBuddies;
	private final boolean doBackBlings;
	private final boolean doLore;
	private final int iconSettings;
	private final long joined;
	private final String role;
	private final String countryCode;
	private final boolean perRegionEffects;
	private final boolean perRegionEffectsSet;
	private final int panorama;
	private final boolean onlineActivity;
	private final Map<String, CapeServer> capeServerSettings;

	/**
	 * @return the UTC timestamp at which this user joined.
	 */
	@Override
	public long getJoinTime() {
		return joined;
	}

	/**
	 * @return the role of the user on the Cosmetica platform. For example, "admin" or "default".
	 */
	@Override
	public String getRole() {
		return role;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public boolean doHats() {
		return doHats;
	}

	@Override
	public boolean doShoulderBuddies() {
		return doShoulderBuddies;
	}

	@Override
	public boolean doBackBlings() {
		return doBackBlings;
	}

	@Override
	public boolean doLore() {
		return doLore;
	}

	@Override
	public int getIconSettings() {
		return this.iconSettings;
	}

	@Override
	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public boolean hasPerRegionEffects() {
		return perRegionEffects;
	}

	@Override
	public boolean hasPerRegionEffectsSet() {
		return perRegionEffectsSet;
	}

	@Override
	public int getPanorama() {
		return panorama;
	}

	@Override
	public boolean doOnlineActivity() {
		return this.onlineActivity;
	}

	@Override
	public Map<String, CapeServer> getCapeServerSettings() {
		return capeServerSettings;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		UserSettingsImpl that = (UserSettingsImpl) obj;
		return Objects.equals(this.uuid, that.uuid) &&
				this.doHats == that.doHats &&
				this.doShoulderBuddies == that.doShoulderBuddies &&
				this.doBackBlings == that.doBackBlings &&
				this.doLore == that.doLore &&
				this.joined == that.joined &&
				Objects.equals(this.role, that.role) &&
				Objects.equals(this.countryCode, that.countryCode) &&
				this.perRegionEffects == that.perRegionEffects &&
				this.perRegionEffectsSet == that.perRegionEffectsSet &&
				this.panorama == that.panorama &&
				Objects.equals(this.capeServerSettings, that.capeServerSettings);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, doHats, doShoulderBuddies, doBackBlings, doLore, joined, role, countryCode, perRegionEffects, perRegionEffectsSet, panorama, capeServerSettings);
	}

	@Override
	public String toString() {
		return "UserSettings[" +
				"uuid=" + uuid + ", " +
				"doHats=" + doHats + ", " +
				"doShoulderBuddies=" + doShoulderBuddies + ", " +
				"doBackBlings=" + doBackBlings + ", " +
				"doLore=" + doLore + ", " +
				"joined=" + joined + ", " +
				"role=" + role + ", " +
				"countryCode=" + countryCode + ", " +
				"perRegionEffects=" + perRegionEffects + ", " +
				"perRegionEffectsSet=" + perRegionEffectsSet + ", " +
				"panorama=" + panorama + ", " +
				"capeServerSettings=" + capeServerSettings + ']';
	}

}
