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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DummyUserSettings implements UserSettings {
	private final UUID uuid = UUID.fromString("b1f4a42f-ec33-4608-a8b0-94911d626840");

	@Override
	public long getJoinTime() {
		return 0;
	}

	@Override
	public String getRole() {
		return "default";
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	@Override
	public boolean doHats() {
		return true;
	}

	@Override
	public boolean doShoulderBuddies() {
		return true;
	}

	@Override
	public boolean doBackBlings() {
		return true;
	}

	@Override
	public boolean doLore() {
		return true;
	}

	@Override
	public String getCountryCode() {
		return "AQ";
	}

	@Override
	public boolean hasPerRegionEffects() {
		return false;
	}

	@Override
	public boolean hasPerRegionEffectsSet() {
		return true;
	}

	@Override
	public int getPanorama() {
		return 0;
	}

	@Override
	public Map<String, CapeServer> getCapeServerSettings() {
		return new HashMap<>();
	}

	@Override
	public int getIconSettings() {
		return 0;
	}

	@Override
	public boolean doOnlineActivity() {
		return false;
	}
}
