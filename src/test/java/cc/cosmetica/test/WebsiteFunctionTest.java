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

import cc.cosmetica.api.*;
import cc.cosmetica.api.cosmetic.Cosmetic;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.LoreType;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Tests for contacting api endpoints that are typically used by the website. Stuff like getting a page of popular cosmetics.
 */
public class WebsiteFunctionTest {
	@Test
	public void testPopular() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		CosmeticsPage<?> page = api.getPopularCosmetics(1, 8).get();
		System.out.println("Contacted popular in " + (System.currentTimeMillis() - time) + "ms");

		for (Cosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}
	}
	
	@Test
	public void testRecent() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		CosmeticsPage<?> page = api.getRecentCosmetics(CosmeticType.SHOULDER_BUDDY, 1, 8).get();
		System.out.println("Contacted recent shoulder buddies in " + (System.currentTimeMillis() - time) + "ms");

		for (Cosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}
	}
	
	@Test
	public void testSystem() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		CosmeticsPage<?> page = api.getOfficialCosmetics(1, 87).get(); // the api caps it at 30 cosmetics
		System.out.println("Contacted system in " + (System.currentTimeMillis() - time) + "ms");

		for (Cosmetic cosmetic : page.getCosmetics()) {
			System.out.println(cosmetic.getName());
		}
	}
	
	@Test
	public void testLoreLists() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		long time = System.currentTimeMillis();
		List<String> pronouns = api.getLoreList(LoreType.PRONOUNS).get();
		System.out.println("Contacted pronoun lore list in " + (System.currentTimeMillis() - time) + "ms");

		if (pronouns.isEmpty()) {
			fail("Pronoun Lore List should not be empty.");
		}

		pronouns.forEach(System.out::println);
	}

	@Test
	public void testCosmeticInfo() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();
		String cosmeticName = api.getCosmetic(CosmeticType.CAPE, "MDhhS1ZiWmpwMVVzT3c").get().getName();

		System.out.println("`MDhhS1ZiWmpwMVVzT3c` is: " + cosmeticName);
		assertEquals(cosmeticName, "PoggerBottle's Cape");
	}

	@Test
	public void testUserInfo() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();
		System.out.println("Valo's lore: " + api.getUserInfo(UUID.fromString("8ea1da2f-0efa-4044-9e6f-4a3bf4e8a9a5"), "Valoeghese").get().getLore());
	}

	@Test
	public void testUserOwnedCosmetics() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();
		System.out.println("Lythogeor's Cosmetics:");
		api.getCosmeticsOwnedBy(UUID.fromString("cd19cb6e-c829-46b3-a6df-63bbe2c5a0dd"), "Lythogeor").get().stream().map(c -> "- " + c.getName() + " (" + c.getType() + ")").forEach(System.out::println);
	}

	@Test(expected = CosmeticaAPIException.class)
	public void testInvalidUserErrors() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		try {
			api.getCosmeticsOwnedBy(null, "asdasdfas937").get();
		}
		catch (CosmeticaAPIException e) {
			if (!e.getMessage().contains("error: no user")) {
				fail("Did not find expected error message 'error: no user'");
			}

			throw e;
		}
	}

	@Test
	public void testReducedInfo() {
		CosmeticaAPI api = CosmeticaAPI.newUnauthenticatedInstance();

		ServerResponse<UserInfo> info = api.getUserInfo(UUID.fromString("8ea1da2f-0efa-4044-9e6f-4a3bf4e8a9a5"), "Valoeghese", true, false);
		System.out.println("Valo's First Hat: " + (info.get().getHats().isEmpty() ? "(no hats equipped)" : info.get().getHats().get(0).getName()));
	}
}