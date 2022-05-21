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

import cc.cosmetica.api.Box;
import cc.cosmetica.api.CosmeticType;
import cc.cosmetica.api.Model;
import cc.cosmetica.api.User;
import cc.cosmetica.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

abstract class BaseModel implements Model {
	BaseModel(String id, int flags, Box bounds) {
		this.id = id;
		this.flags = flags;
		this.bounds = bounds;
	}

	private final String id;
	private final int flags;
	private final Box bounds;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int flags() {
		return this.flags;
	}

	@Override
	public Box getBoundingBox() {
		return this.bounds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseModel that = (BaseModel) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	static Optional<Model> parse(@Nullable JsonObject json) {
		if (json == null) return Optional.empty();

		String id = json.get("id").getAsString();
		int flags = json.get("extra info").getAsInt();
		JsonArray unparsedBounds = json.get("bounds").getAsJsonArray();
		JsonArray lowerBounds = unparsedBounds.get(0).getAsJsonArray();
		JsonArray upperBounds = unparsedBounds.get(1).getAsJsonArray();

		Box bounds = new Box(
				lowerBounds.get(0).getAsInt(),
				lowerBounds.get(1).getAsInt(),
				lowerBounds.get(2).getAsInt(),
				upperBounds.get(0).getAsInt(),
				upperBounds.get(1).getAsInt(),
				upperBounds.get(2).getAsInt());

		if (id.charAt(0) == '-') { // built-in
			return Optional.of(new BuiltInModel(id, flags, bounds));
		}
		else {
			return Optional.of(new CustomModelImpl(
					CosmeticType.fromTypeString(json.get("type").getAsString()).get(),
					id,
					json.get("name").getAsString(),
					flags,
					bounds,
					json.get("model").getAsString(),
					json.get("texture").getAsString(),
					new User(Util.fromUUID(json.get("owner").getAsString()), json.get("ownerName").getAsString()),
					json.get("uploaded").getAsLong(),
					json.get("usesUvRotations").getAsBoolean()
			));
		}
	}
}
