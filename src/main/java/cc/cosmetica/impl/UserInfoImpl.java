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

import cc.cosmetica.api.Cape;
import cc.cosmetica.api.Model;
import cc.cosmetica.api.ShoulderBuddies;
import cc.cosmetica.api.UserInfo;

import java.util.List;
import java.util.Optional;

public class UserInfoImpl implements UserInfo {
	public UserInfoImpl(String skin, boolean slim, String lore, String platform, String role, boolean upsideDown, String prefix, String suffix, List<Model> hats, Optional<ShoulderBuddies> shoulderBuddies, Optional<Model> backBling, Optional<Cape> cape) {
		this.skin = skin;
		this.slim = slim;
		this.lore = lore;
		this.platform = platform;
		this.role = role;
		this.upsideDown = upsideDown;
		this.prefix = prefix;
		this.suffix = suffix;
		this.hats = hats;
		this.shoulderBuddies = shoulderBuddies;
		this.backBling = backBling;
		this.cape = cape;
	}

	private final String skin;
	private final boolean slim;
	private final String lore;
	private final String platform;
	private final String role;
	private final boolean upsideDown;
	private final String prefix;
	private final String suffix;
	private final List<Model> hats;
	private final Optional<ShoulderBuddies> shoulderBuddies;
	private final Optional<Model> backBling;
	private final Optional<Cape> cape;

	@Override
	public String getSkin() {
		return this.skin;
	}

	@Override
	public boolean isSlim() {
		return this.slim;
	}

	@Override
	public String getLore() {
		return lore;
	}

	@Override
	public String getPlatform() {
		return platform;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public boolean isUpsideDown() {
		return upsideDown;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public List<Model> getHats() {
		return hats;
	}

	@Override
	public Optional<ShoulderBuddies> getShoulderBuddies() {
		return shoulderBuddies;
	}

	@Override
	public Optional<Model> getBackBling() {
		return backBling;
	}

	@Override
	public Optional<Cape> getCape() {
		return cape;
	}

	@Override
	public String toString() {
		return "UserInfo[" +
				"lore=" + lore + ", " +
				"upsideDown=" + upsideDown + ", " +
				"prefix=" + prefix + ", " +
				"suffix=" + suffix + ", " +
				"hats=" + hats + ", " +
				"shoulderBuddies=" + shoulderBuddies + ", " +
				"backBling=" + backBling + ", " +
				"cape=" + cape + ']';
	}
}
