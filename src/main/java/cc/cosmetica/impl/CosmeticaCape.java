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
import cc.cosmetica.api.CustomCape;
import cc.cosmetica.api.User;

class CosmeticaCape extends BaseCape implements CustomCape {
	CosmeticaCape(String id, String name, String origin, String image, boolean cosmeticaAlternative, int frameDelay, long uploadTime, User owner) {
		super(id, name, origin, image, cosmeticaAlternative, frameDelay);

		this.owner = owner;
		this.uploadTime = uploadTime;
	}

	private final User owner;
	private final long uploadTime;

	@Override
	public User getOwner() {
		return this.owner;
	}

	@Override
	public long getUploadTime() {
		return this.uploadTime;
	}

	@Override
	public CosmeticType<?> getType() {
		return CosmeticType.CAPE;
	}
}
