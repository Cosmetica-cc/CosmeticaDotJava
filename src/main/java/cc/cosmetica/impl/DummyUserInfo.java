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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DummyUserInfo implements UserInfo {
	@Override
	public String getLore() {
		return "";
	}

	@Override
	public String getPlatform() {
		return "java";
	}

	@Override
	public boolean isUpsideDown() {
		return false;
	}

	@Override
	public String getPrefix() {
		return "";
	}

	@Override
	public String getSuffix() {
		return "";
	}

	@Override
	public List<Model> getHats() {
		return new ArrayList<>();
	}

	@Override
	public Optional<ShoulderBuddies> getShoulderBuddies() {
		return Optional.empty();
	}

	@Override
	public Optional<Model> getBackBling() {
		return Optional.empty();
	}

	@Override
	public Optional<Cape> getCape() {
		return Optional.empty();
	}
}
