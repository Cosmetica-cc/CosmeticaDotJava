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

package cc.cosmetica.test;

import cc.cosmetica.api.CosmeticType;
import cc.cosmetica.api.CosmeticaAPI;
import cc.cosmetica.api.CosmeticsPage;
import cc.cosmetica.api.CustomCosmetic;

import java.util.Optional;

/**
 * Tests for contacting api endpoints that are typically used by the website. Stuff like getting a page of popular cosmetics.
 */
public class WebsiteFunctionTests {
	public static void main(String[] args) {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		CosmeticsPage<?> page = api.getPopularCosmetics(1, 8).getOrThrow();
		System.out.println("Contacted popular in " + (System.currentTimeMillis() - time) + "ms");

		for (CustomCosmetic cosmetic : page.cosmetics()) {
			System.out.println(cosmetic.getName());
		}

		System.out.println();

		time = System.currentTimeMillis();
		page = api.getRecentCosmetics(CosmeticType.SHOULDER_BUDDY, 1, 8, Optional.empty()).getOrThrow();
		System.out.println("Contacted recent in " + (System.currentTimeMillis() - time) + "ms");

		for (CustomCosmetic cosmetic : page.cosmetics()) {
			System.out.println(cosmetic.getName());
		}
	}
}
