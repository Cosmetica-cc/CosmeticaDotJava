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

import cc.cosmetica.api.CosmeticaAPI;
import cc.cosmetica.api.CustomCosmetic;

/**
 * Tests for contacting api endpoints that are typically used by the website. Stuff like getting a page of popular cosmetics.
 */
public class WebsiteFunctionTests {
	public static void main(String[] args) {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		api.getPopularCosmetics(1, 6).ifSuccessfulOrElse(page -> {
			for (CustomCosmetic cosmetic : page.cosmetics()) {
				System.out.println(cosmetic.getName());
			}
		}, e -> {throw new RuntimeException(e);});
	}
}