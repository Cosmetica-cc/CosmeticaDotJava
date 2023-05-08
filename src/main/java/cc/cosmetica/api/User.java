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

package cc.cosmetica.api;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a cosmetica user. If from an endpoint that generates reduced data, the username may be blank.
 */
public final class User {
	public User(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
	}

	private final UUID uuid;
	private final String username;

	public UUID getUUID() {
		return uuid;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		User that = (User) obj;
		return Objects.equals(this.uuid, that.uuid) &&
				Objects.equals(this.username, that.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, username);
	}

	@Override
	public String toString() {
		return "User[" +
				"uuid=" + uuid + ", " +
				"username=" + username + ']';
	}

}
