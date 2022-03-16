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

import cc.cosmetica.api.CustomCape;

class CosmeticaCape extends BaseCape implements CustomCape {
	CosmeticaCape(String id, String origin, String image, boolean cosmeticaAlternative, String name, String owner) {
		super(id, origin, image, cosmeticaAlternative);

		this.name = name;
		this.owner = owner;
	}

	private final String name;
	private final String owner;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}
}
