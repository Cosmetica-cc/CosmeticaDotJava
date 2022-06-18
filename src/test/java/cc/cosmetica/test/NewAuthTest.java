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

import cc.cosmetica.impl.CosmeticaWebAPI;

import java.util.Scanner;
import java.util.UUID;

public class NewAuthTest {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Minecraft Username");
		String username = scanner.nextLine();

		System.out.println("Minecraft UUID Dashed");
		UUID uuid = UUID.fromString(scanner.nextLine());

		System.out.println("Minecraft Access Token");
		String token = scanner.nextLine();

		CosmeticaWebAPI.fromMinecraftToken(token, username, uuid);
	}
}
