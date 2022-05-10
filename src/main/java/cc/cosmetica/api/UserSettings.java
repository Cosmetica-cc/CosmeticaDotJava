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
import java.util.UUID;

/**
 * A record containing a user's settings and preferences.
 */
public record UserSettings(UUID uuid, String capeId, String hatId, boolean doHats, String shoulderBuddyId, boolean doShoulderBuddies, String lore, boolean doLore, long joined, String role, String countryCode, boolean perRegionEffects, boolean perRegionEffectsSet, int panorama, Map<String, CapeDisplay> capeSettings) {
	/**
	 * @return the UTC timestamp at which this user joined.
	 */
	@Override
	public long joined() {
		return joined;
	}

	/**
	 * @return the role of the user on the Cosmetica platform. For example, "admin" or "default".
	 */
	@Override
	public String role() {
		return role;
	}
}
