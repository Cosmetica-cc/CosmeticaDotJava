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
import cc.cosmetica.api.User;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

class BaseCape implements Cape {
	BaseCape(String id, String origin, String image, boolean cosmeticaAlternative) {
		this.id = id;
		this.origin = origin;
		this.image = image;
		this.cosmeticaAlternative = cosmeticaAlternative;
	}

	private final String id;
	private final String origin;
	private final String image;
	private final boolean cosmeticaAlternative;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getOrigin() {
		return this.origin;
	}

	@Override
	public String getImage() {
		return this.image;
	}

	@Override
	public boolean isCosmeticaAlternative() {
		return this.cosmeticaAlternative;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseCape baseCape = (BaseCape) o;
		return id.equals(baseCape.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	static Optional<Cape> parse(@Nullable JsonObject data) {
		if (data == null) return Optional.empty();

		String id = data.get("id").getAsString();
		String origin = data.get("origin").getAsString();
		String image = data.get("image").getAsString();
		boolean cosmeticaAlternative = data.get("isCosmeticaAlternative").getAsBoolean();

		if ("Cosmetica".equals(origin)) {
			User owner = new User(UUID.fromString(data.get("owner").getAsString()), data.get("ownerName").getAsString());
			return Optional.of(new CosmeticaCape(id, origin, image, cosmeticaAlternative, data.get("uploaded").getAsLong(), data.get("name").getAsString(), owner));
		}
		else {
			return Optional.of(new BaseCape(id, origin, image, cosmeticaAlternative));
		}
	}
}
