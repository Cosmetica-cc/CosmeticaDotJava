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

package cc.cosmetica.impl.cosmetic;

import cc.cosmetica.api.User;
import cc.cosmetica.api.cosmetic.BoundingBox;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.Model;
import cc.cosmetica.api.cosmetic.UploadState;
import cc.cosmetica.util.Yootil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

class ModelImpl extends AbstractCosmetic implements Model {
	private ModelImpl(CosmeticType<?> type, String id, String name, int flags, BoundingBox bounds,
			  String model, String base64Texture, User owner, String origin,
			  UploadState uploadState, String reason, long uploadTime, boolean usesUVRotations) {
		this.id = id;
		this.flags = flags;
		this.bounds = bounds;

		this.model = model;
		this.texture = base64Texture;
		this.owner = owner;
		this.usesUVRotations = usesUVRotations;
		this.type = type;
		this.name = name;
		this.origin = origin;

		this.uploadTime = uploadTime;
		this.uploadState = uploadState;
		this.reason = reason;
	}

	private final String id;
	private final int flags;
	private final BoundingBox bounds;

	private final String model;
	private final String texture;
	private final User owner;
	private final boolean usesUVRotations;
	private final CosmeticType<?> type;
	private final String name;
	private final String origin;

	private final long uploadTime;
	private final UploadState uploadState;
	private final String reason;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int flags() {
		return this.flags;
	}

	@Override
	public int getFrameDelay() {
		return 50 * ((this.flags >> 4) & 0x1F);
	}

	@Override
	public BoundingBox getBoundingBox() {
		return this.bounds;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public String getTexture() {
		return this.texture;
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
	public boolean usesUVRotations() {
		return this.usesUVRotations;
	}

	@Override
	public boolean isBuiltin() {
		return this.id.charAt(0) == '-';
	}

	@Override
	public CosmeticType<?> getType() {
		return this.type;
	}

	@Override
	public String getName() {
		return this.name;
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
		return false;
	}

	static Optional<Model> parseAsModel(@Nullable JsonObject json) {
		if (json == null) {
			return Optional.empty();
		}

		Optional<CosmeticType<?>> type = CosmeticType.fromTypeString(json.get("type").getAsString());

		if (!type.isPresent()) {
			return Optional.empty();
		}

		String id = json.get("id").getAsString();
		int flags = json.get("extraInfo").getAsInt();
		JsonArray unpasedBounds = json.get("bounds").getAsJsonArray();
		JsonArray lowerBounds = unpasedBounds.get(0).getAsJsonArray();
		JsonArray upperBounds = unpasedBounds.get(1).getAsJsonArray();

		BoundingBox bounds = new BoundingBox(
				lowerBounds.get(0).getAsInt(),
				lowerBounds.get(1).getAsInt(),
				lowerBounds.get(2).getAsInt(),
				upperBounds.get(0).getAsInt(),
				upperBounds.get(1).getAsInt(),
				upperBounds.get(2).getAsInt());

		return Optional.of(new ModelImpl(
				type.get(),
				id,
				json.get("name").getAsString(),
				flags,
				bounds,
				json.get("model").getAsString(),
				json.get("texture").getAsString(),
				new User(Yootil.toUUID(json.get("owner").getAsString()), json.get("ownerName").getAsString()),
				json.get("origin").getAsString(),
				UploadState.getById(json.get("uploadState").getAsInt()),
				json.get("reason").getAsString(),
				json.get("uploaded").getAsLong(),
				json.get("usesUvRotations").getAsBoolean()
		));
	}
}
