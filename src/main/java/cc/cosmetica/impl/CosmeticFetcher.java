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
import cc.cosmetica.api.CosmeticaAPI;
import cc.cosmetica.api.CustomCape;
import cc.cosmetica.api.Model;
import org.jetbrains.annotations.Nullable;

/**
 * Class to hide that my solution to stopping code duplication is to literally just store a copy of {@link CosmeticaWebAPI} internally.
 */
public final class CosmeticFetcher {
	private CosmeticFetcher() {
		// NO-OP, prevent construction of instances
		// kinda useless but it's "goOd prACTise".
	}

	private static final CosmeticaAPI API = CosmeticaWebAPI.newUnauthenticatedInstance();

	@Nullable
	public static CustomCape getCape(String id) {
		return API.getCosmetic(CosmeticType.CAPE, id).getOrNull();
	}

	@Nullable
	public static Model getModel(CosmeticType<Model> type, String id) {
		return API.getCosmetic(type, id).getOrNull();
	}
}
