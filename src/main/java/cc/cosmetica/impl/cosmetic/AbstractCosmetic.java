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

package cc.cosmetica.impl.cosmetic;

import cc.cosmetica.api.cosmetic.Cosmetic;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.UploadState;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * An abstract cosmetic that declares some basic shared functionality.
 */
public abstract class AbstractCosmetic implements Cosmetic {
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o instanceof Cosmetic) {
			Cosmetic otherCosmetic = (Cosmetic) o;
			return this.getId().equals(otherCosmetic.getId()) && this.getType().equals(otherCosmetic.getType());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getType(), this.getId());
	}

	/**
	 * Parse a {@link JsonObject} from the API representing a cosmetic into a an actual {@link Cosmetic} object.
	 * @param json the json object representation.
	 * @return an optional containing the parsed cosmetic object, or empty.
	 */
	public static Optional<? extends Cosmetic> parse(@Nullable JsonObject json) {
		if (json == null) {
			return Optional.empty();
		}

		CosmeticType<?> type = CosmeticType.fromTypeString(json.get("type").getAsString()).get();

		if ("".equals(json.get("extraInfo").getAsString())) {
			return SimpleCosmetic.parseAsSimple(json);
		}
		else if (type == CosmeticType.CAPE) {
			return CapeImpl.parseAsCape(json);
		}
		else {
			return ModelImpl.parseAsModel(json);
		}
	}

	public static SimpleCosmetic createDummy() {
		return new SimpleCosmetic(
				CosmeticType.CAPE,
				"Dummy Cape",
				"DUMMY",
				"Cosmetica",
				UUID.randomUUID(),
				UploadState.APPROVED,
				"",
				0
		);
	}
}
