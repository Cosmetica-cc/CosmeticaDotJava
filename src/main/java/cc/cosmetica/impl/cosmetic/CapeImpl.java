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

import cc.cosmetica.api.*;
import cc.cosmetica.api.cosmetic.Cape;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.UploadState;
import cc.cosmetica.util.Yootil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;

class CapeImpl extends AbstractCosmetic implements Cape {
	private CapeImpl(String id, String name, String origin, String image, boolean cosmeticaAlternative, int frameDelay,
			 UploadState uploadState, String reason, long uploadTime, @Nullable User owner) {
		this.id = id;
		this.name = name;
		this.origin = origin;
		this.image = image;
		this.cosmeticaAlternative = cosmeticaAlternative;
		this.frameDelay = frameDelay;

		this.owner = owner;
		this.uploadState = uploadState;
		this.reason = reason;
		this.uploadTime = uploadTime;
	}

	private final String id;
	private final String name;
	private final String origin;
	private final String image;
	private final boolean cosmeticaAlternative;
	private final int frameDelay;

	private final @Nullable User owner;
	private final UploadState uploadState;
	private final String reason;
	private final long uploadTime;

	@Override
	public String getId() {
		return this.id;
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
	public String getImage() {
		return this.image;
	}

	@Override
	public boolean isCosmeticaAlternative() {
		return this.cosmeticaAlternative;
	}

	@Override
	public int getFrameDelay() {
		return this.frameDelay;
	}

	@Override
	public Optional<User> getOwner() {
		return Optional.ofNullable(this.owner);
	}

	@Override
	public long getUploadTime() {
		return this.uploadTime;
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

	@Override
	public CosmeticType<?> getType() {
		return CosmeticType.CAPE;
	}

	static Optional<Cape> parseAsCape(@Nullable JsonObject data) {
		if (data == null) return Optional.empty();

		String id = data.get("id").getAsString();
		String origin = data.get("origin").getAsString();
		String image = data.get("image").getAsString();
		String name = data.get("name").getAsString();
		int frameDelay = data.get("extraInfo").getAsInt();

		boolean cosmeticaAlternative = data.get("isCosmeticaAlternative").getAsBoolean();

		User owner = new User(Yootil.toUUID(data.get("owner").getAsString()), data.get("ownerName").getAsString());
		return Optional.of(new CapeImpl(id, name, origin, image, cosmeticaAlternative, frameDelay,
				UploadState.getById(data.get("uploadState").getAsInt()), data.get("reason").getAsString(), data.get("uploaded").getAsLong(), owner));
	}
}
