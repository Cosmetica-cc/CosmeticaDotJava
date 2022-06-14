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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A record containing the response of a user info get request.
 */
public final class UserInfo {
	public UserInfo(String lore, boolean upsideDown, String prefix, String suffix, List<Model> hats, Optional<Model> shoulderBuddy, Optional<Model> backBling, Optional<Cape> cape) {
		this.lore = lore;
		this.upsideDown = upsideDown;
		this.prefix = prefix;
		this.suffix = suffix;
		this.hats = hats;
		this.shoulderBuddy = shoulderBuddy;
		this.backBling = backBling;
		this.cape = cape;
	}

	private final String lore;
	private final boolean upsideDown;
	private final String prefix;
	private final String suffix;
	private final List<Model> hats;
	private final Optional<Model> shoulderBuddy;
	private final Optional<Model> backBling;
	private final Optional<Cape> cape;

	public String lore() {
		return lore;
	}

	public boolean upsideDown() {
		return upsideDown;
	}

	public String prefix() {
		return prefix;
	}

	public String suffix() {
		return suffix;
	}

	public List<Model> hats() {
		return hats;
	}

	public Optional<Model> shoulderBuddy() {
		return shoulderBuddy;
	}

	public Optional<Model> backBling() {
		return backBling;
	}

	public Optional<Cape> cape() {
		return cape;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (UserInfo) obj;
		return Objects.equals(this.lore, that.lore) &&
				this.upsideDown == that.upsideDown &&
				Objects.equals(this.prefix, that.prefix) &&
				Objects.equals(this.suffix, that.suffix) &&
				Objects.equals(this.hats, that.hats) &&
				Objects.equals(this.shoulderBuddy, that.shoulderBuddy) &&
				Objects.equals(this.backBling, that.backBling) &&
				Objects.equals(this.cape, that.cape);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lore, upsideDown, prefix, suffix, hats, shoulderBuddy, backBling, cape);
	}

	@Override
	public String toString() {
		return "UserInfo[" +
				"lore=" + lore + ", " +
				"upsideDown=" + upsideDown + ", " +
				"prefix=" + prefix + ", " +
				"suffix=" + suffix + ", " +
				"hats=" + hats + ", " +
				"shoulderBuddy=" + shoulderBuddy + ", " +
				"backBling=" + backBling + ", " +
				"cape=" + cape + ']';
	}

}
