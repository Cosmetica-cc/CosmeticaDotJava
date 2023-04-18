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
