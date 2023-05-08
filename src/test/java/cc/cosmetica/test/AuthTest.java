/*
 * Copyright 2022, 2023 EyezahMC
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
import cc.cosmetica.api.CosmeticaAPIException;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class AuthTest {
	@Test(expected = CosmeticaAPIException.class)
	public void testExchangeTokensInvalidToken() throws IOException {
		System.out.println("Expecting invalid token:");

		try {
			CosmeticaAPI.fromMinecraftToken("ooga booga", "Valoeghese", UUID.fromString("8ea1da2f-0efa-4044-9e6f-4a3bf4e8a9a5"));
		} catch (CosmeticaAPIException e) {
			System.out.println(e.getMessage());

			if ("API server request to https://auth.cosmetica.cc/verify responded with error: invalid token".equals(e.getMessage())) {
				throw e;
			}
		}
	}
}
