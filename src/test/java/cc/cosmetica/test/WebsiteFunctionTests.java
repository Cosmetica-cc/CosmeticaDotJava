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
import cc.cosmetica.api.CosmeticaAPIException;
import cc.cosmetica.api.CosmeticsPage;
import cc.cosmetica.api.CustomCosmetic;
import cc.cosmetica.api.LoreType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Tests for contacting api endpoints that are typically used by the website. Stuff like getting a page of popular cosmetics.
 */
public class WebsiteFunctionTests {
	public static void main(String[] args) {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		CosmeticsPage<?> page = api.getPopularCosmetics(1, 8).get();
		System.out.println("Contacted popular in " + (System.currentTimeMillis() - time) + "ms");

		for (CustomCosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}

		System.out.println();

		// recent
		time = System.currentTimeMillis();
		page = api.getRecentCosmetics(CosmeticType.SHOULDER_BUDDY, 1, 8, Optional.empty()).get();
		System.out.println("Contacted recent in " + (System.currentTimeMillis() - time) + "ms");

		for (CustomCosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}

		System.out.println();

		// system
		time = System.currentTimeMillis();
		page = api.getOfficialCosmetics(1, 87).get(); // the api caps it at 30 cosmetics
		System.out.println("Contacted system in " + (System.currentTimeMillis() - time) + "ms");

		for (CustomCosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}

		System.out.println();

		// lore lists
		time = System.currentTimeMillis();
		List<String> pronouns = api.getLoreList(LoreType.PRONOUNS).get();
		System.out.println("Contacted lore lists in " + (System.currentTimeMillis() - time) + "ms");
		pronouns.forEach(System.out::println);

		System.out.println();

		// cosmetic
		System.out.println(api.getCosmetic(CosmeticType.CAPE, "MDhhS1ZiWmpwMVVzT3c").get().getName());

		// info
		System.out.println("Valo's lore: " + api.getUserInfo(null, "Valoeghese").get().getLore());

		// user owned cosmetics
		System.out.println();
		System.out.println("Lythogeor's Cosmetics:");
		api.getCosmeticsOwnedBy(UUID.fromString("cd19cb6e-c829-46b3-a6df-63bbe2c5a0dd"), "Lythogeor").get().stream().map(c -> "- " + c.getName() + " (" + c.getType() + ")").forEach(System.out::println);

		// catch an error from user owned cosmetics
		System.out.println();
		System.out.println("The following should be an error:");

		try {
			api.getCosmeticsOwnedBy(null, "asdasdfas937").get();
		}
		catch (CosmeticaAPIException e) {
			e.printStackTrace();
		}
	}
}
