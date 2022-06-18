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

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * An object containing a user's settings and preferences.
 */
// TODO split such a fat object into api and impl so api readers don't have to see this monstrosity
public final class UserSettings {
	public UserSettings(UUID uuid, String capeId, String hatId, boolean doHats, String shoulderBuddyId, boolean doShoulderBuddies, String lore, boolean doLore, long joined, String role, String countryCode, boolean perRegionEffects, boolean perRegionEffectsSet, int panorama, Map<String, CapeServer> capeServerSettings) {
		this.uuid = uuid;
		this.capeId = capeId;
		this.hatId = hatId;
		this.doHats = doHats;
		this.shoulderBuddyId = shoulderBuddyId;
		this.doShoulderBuddies = doShoulderBuddies;
		this.lore = lore;
		this.doLore = doLore;
		this.joined = joined;
		this.role = role;
		this.countryCode = countryCode;
		this.perRegionEffects = perRegionEffects;
		this.perRegionEffectsSet = perRegionEffectsSet;
		this.panorama = panorama;
		this.capeServerSettings = capeServerSettings;
	}

	private final UUID uuid;
	private final String capeId;
	private final String hatId;
	private final boolean doHats;
	private final String shoulderBuddyId;
	private final boolean doShoulderBuddies;
	private final String lore;
	private final boolean doLore;
	private final long joined;
	private final String role;
	private final String countryCode;
	private final boolean perRegionEffects;
	private final boolean perRegionEffectsSet;
	private final int panorama;
	private final Map<String, CapeServer> capeServerSettings;

	/**
	 * @return the UTC timestamp at which this user joined.
	 */
	public long getJoinTime() {
		return joined;
	}

	/**
	 * @return the role of the user on the Cosmetica platform. For example, "admin" or "default".
	 */
	public String getRole() {
		return role;
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getCapeId() {
		return capeId;
	}

	public String getHatId() {
		return hatId;
	}

	public boolean doHats() {
		return doHats;
	}

	public String getShoulderBuddyId() {
		return shoulderBuddyId;
	}

	public boolean doShoulderBuddies() {
		return doShoulderBuddies;
	}

	public String getLore() {
		return lore;
	}

	public boolean doLore() {
		return doLore;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public boolean hasPerRegionEffects() {
		return perRegionEffects;
	}

	public boolean hasPerRegionEffectsSet() {
		return perRegionEffectsSet;
	}

	public int getPanorama() {
		return panorama;
	}

	public Map<String, CapeServer> getCapeServerSettings() {
		return capeServerSettings;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		UserSettings that = (UserSettings) obj;
		return Objects.equals(this.uuid, that.uuid) &&
				Objects.equals(this.capeId, that.capeId) &&
				Objects.equals(this.hatId, that.hatId) &&
				this.doHats == that.doHats &&
				Objects.equals(this.shoulderBuddyId, that.shoulderBuddyId) &&
				this.doShoulderBuddies == that.doShoulderBuddies &&
				Objects.equals(this.lore, that.lore) &&
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
		return Objects.hash(uuid, capeId, hatId, doHats, shoulderBuddyId, doShoulderBuddies, lore, doLore, joined, role, countryCode, perRegionEffects, perRegionEffectsSet, panorama, capeServerSettings);
	}

	@Override
	public String toString() {
		return "UserSettings[" +
				"uuid=" + uuid + ", " +
				"capeId=" + capeId + ", " +
				"hatId=" + hatId + ", " +
				"doHats=" + doHats + ", " +
				"shoulderBuddyId=" + shoulderBuddyId + ", " +
				"doShoulderBuddies=" + doShoulderBuddies + ", " +
				"lore=" + lore + ", " +
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
