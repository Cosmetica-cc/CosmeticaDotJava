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

import cc.cosmetica.api.*;
import cc.cosmetica.api.cosmetic.Cosmetic;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.UploadState;
import cc.cosmetica.util.Yootil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * A simple cosmetic which only has surface info. More info can be loaded by looking up the cosmetic at {@link cc.cosmetica.api.CosmeticaAPI#getCosmetic(CosmeticType, String)}.
 * @since 2.0.0
 */
public class SimpleCosmetic implements Cosmetic {
	private SimpleCosmetic(CosmeticType<?> type, String name, String id, String origin, UUID ownerUUID,
						   UploadState uploadState, String reason, long uploadTime) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.origin = origin;
		this.owner = new User(ownerUUID, "");
		this.uploadTime = uploadTime;
		this.uploadState = uploadState;
		this.reason = reason;
	}

	private final CosmeticType<?> type;
	private final String name;
	private final String id;
	private final String origin;
	private final User owner;
	private final long uploadTime;
	private final UploadState uploadState;
	private final String reason;

	@Override
	public CosmeticType<?> getType() {
		return this.type;
	}

	@Override
	public Optional<User> getOwner() {
		return Optional.of(this.owner);
	}

	@Override
	public long getUploadTime() {
		return this.uploadTime;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getOrigin() {
		return this.origin;
	}

	@Override
	public UploadState getUploadState() {
		return this.uploadState;
	}

	@Override
	public String getReason() {
		return this.reason;
	}

	@Override
	public boolean hasReducedData() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cosmetic cosmetic = (Cosmetic) o;
		return this.id.equals(cosmetic.getId()) && this.type.equals(cosmetic.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.id);
	}

	/**
	 * Parse the given json object to a simple cosmetic.
	 * @param data the json object to parse.
	 * @return the SimpleCosmetic instance.
	 */
	static Optional<SimpleCosmetic> parse(@Nullable JsonObject data) {
		if (data == null) {
			return Optional.empty();
		}

		Optional<CosmeticType<?>> type = CosmeticType.fromTypeString(data.get("type").getAsString());

		return type.map(cosmeticType -> new SimpleCosmetic(
				cosmeticType,
				data.get("name").getAsString(),
				data.get("id").getAsString(),
				data.get("origin").getAsString(),
				Yootil.toUUID(data.get("owner").getAsString()),
				UploadState.getById(data.get("uploadState").getAsInt()),
				data.get("reason").getAsString(),
				data.get("uploaded").getAsLong()
		));
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
