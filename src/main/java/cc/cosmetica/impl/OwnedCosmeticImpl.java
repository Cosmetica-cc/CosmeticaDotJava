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

import cc.cosmetica.api.CosmeticType;
import cc.cosmetica.api.OwnedCosmetic;
import cc.cosmetica.api.UploadState;
import com.google.gson.JsonObject;

/**
 * Implementation of OwnedCosmetic.
 */
class OwnedCosmeticImpl implements OwnedCosmetic {
	private OwnedCosmeticImpl(CosmeticType<?> type, String origin, String name, String id,
		long uploadTime, int users, int uploadState) {
		this.type = type;
		this.origin = origin;
		this.name = name;
		this.id = id;
		this.uploadTime = uploadTime;
		this.users = users;
		this.uploadState = UploadState.getById(uploadState);
	}

	private final CosmeticType<?> type;
	private final String origin;
	private final String name;
	private final String id;
	private final long uploadTime;
	private final int users;
	private final UploadState uploadState;

	@Override
	public CosmeticType<?> getType() {
		return this.type;
	}

	@Override
	public String getOrigin() {
		return this.origin;
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
	public long getUploadTime() {
		return this.uploadTime;
	}

	@Override
	public int getUsers() {
		return this.users;
	}

	@Override
	public UploadState getUploadState() {
		return this.uploadState;
	}

	static OwnedCosmetic parse(JsonObject object) {
		return new OwnedCosmeticImpl(
				CosmeticType.fromTypeString(object.get("type").getAsString()).get(),
				object.get("origin").getAsString(),
				object.get("name").getAsString(),
				object.get("id").getAsString(),
				object.get("uploaded").getAsLong(),
				object.get("users").getAsInt(),
				object.get("uploadState").getAsInt()
		);
	}
}
