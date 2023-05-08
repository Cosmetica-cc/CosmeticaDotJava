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

import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.CosmeticaAPI;
import com.google.gson.JsonParser;

import java.util.Scanner;
import java.util.UUID;

public class OldAuthTest {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Minecraft Username");
		String username = scanner.nextLine();

		System.out.println("Minecraft UUID Dashed");
		UUID uuid = UUID.fromString(scanner.nextLine());

		System.out.println("Minecraft Access Token");
		String token = scanner.nextLine();

		System.out.println("Upload test model? y/n");
		boolean uploadModel = scanner.nextLine().charAt(0) == 'y';

		CosmeticaAPI api = CosmeticaAPI.fromMinecraftToken(token, username, uuid);

		if (uploadModel) { // NOTE: Will respond by throwing the error "error: duplicate" if the upload is successful because the model here is already a cosmetic on cosmetica.
			System.out.println("Uploading Model");
			final String fezTexture = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAABdUExURbIQEOURAlYHB7MQELMPEL0QDbwQDbsRDbwQDrsQDsYQCsYRCsYQC8UQCsUQC9AQB88RCM8QCM4RCNoRBdkRBtkRBdgQBdgRBdgQBuQRAuMRAuMRA+IRA+ISAwAAAI48HqkAAAAfdFJOU////////////////////////////////////////wDNGXYQAAAACXBIWXMAAA6/AAAOvwE4BVMkAAAAmElEQVQ4T+WQXQ+CMAxF6xAcOHWI+MF0//9nYuE2UWufjL54kmW57UmWOxIWIM84nIz1h4K78yTwgBFhAvtfCY9QsaSCBWQFlWVVrXztkRXUrEMTwmaLrKBdjG0b4x5ZQV136I+n/oysoMuQ0vWWBmQFt3BTTwxe4RbO+9oW0MIW0MIW0GL++DdIC1uQ23xC7u8LJn8h5DwCMq1dKQQrzKwAAAAASUVORK5CYII=";
			final String fezJason = "{\"credit\":\"Made with Blockbench\",\"texture_size\":[32,32],\"textures\":{\"0\":\"hats:fez\",\"particle\":\"hats:fez\"},\"elements\":[{\"name\":\"fez\",\"from\":[5,0,5],\"to\":[11,6,11],\"faces\":{\"north\":{\"uv\":[0,4,4,7],\"texture\":\"#0\"},\"east\":{\"uv\":[0,4,4,7],\"texture\":\"#0\"},\"south\":{\"uv\":[0,4,4,7],\"texture\":\"#0\"},\"west\":{\"uv\":[0,4,4,7],\"texture\":\"#0\"},\"up\":{\"uv\":[0,0,3.5,3.5],\"texture\":\"#0\"},\"down\":{\"uv\":[4,0,8,4],\"texture\":\"#0\"}}},{\"name\":\"ropetuft\",\"from\":[12,2,7],\"to\":[13,5,9],\"faces\":{\"north\":{\"uv\":[11.5,1.5,12,3.5],\"texture\":\"#0\"},\"east\":{\"uv\":[12,1.5,13.5,3.5],\"texture\":\"#0\"},\"south\":{\"uv\":[13.5,1.5,14,3.5],\"texture\":\"#0\"},\"west\":{\"uv\":[10,1.5,11.5,3.5],\"texture\":\"#0\"},\"up\":{\"uv\":[11.5,0,12,1.5],\"texture\":\"#0\"},\"down\":{\"uv\":[13.5,0,14,1.5],\"texture\":\"#0\"}}},{\"from\":[4,0,6],\"to\":[5,6,10],\"faces\":{\"north\":{\"uv\":[3,7,3.5,10],\"texture\":\"#0\"},\"east\":{\"uv\":[0,0,2,3],\"texture\":\"#missing\"},\"south\":{\"uv\":[0,7,0.5,10],\"texture\":\"#0\"},\"west\":{\"uv\":[0.5,7,1.5,10],\"texture\":\"#0\"},\"up\":{\"uv\":[3.5,7,4.5,9],\"texture\":\"#0\"},\"down\":{\"uv\":[4,0,4.5,2],\"texture\":\"#0\"}}},{\"from\":[11,0,6],\"to\":[12,6,10],\"rotation\":{\"angle\":0,\"axis\":\"y\",\"origin\":[12,0,8]},\"faces\":{\"north\":{\"uv\":[0,7,0.5,10],\"texture\":\"#0\"},\"east\":{\"uv\":[0.5,7,3,10],\"texture\":\"#0\"},\"south\":{\"uv\":[3,7,3.5,10],\"texture\":\"#0\"},\"west\":{\"uv\":[0,0,2,3],\"texture\":\"#missing\"},\"up\":{\"uv\":[3.5,7,4.5,12],\"rotation\":180,\"texture\":\"#0\"},\"down\":{\"uv\":[4,0,4.5,2],\"rotation\":180,\"texture\":\"#0\"}}},{\"from\":[6,0,11],\"to\":[10,6,12],\"rotation\":{\"angle\":0,\"axis\":\"y\",\"origin\":[9,0,12]},\"faces\":{\"north\":{\"uv\":[0,0,2,3],\"texture\":\"#missing\"},\"east\":{\"uv\":[0,7,0.5,10],\"texture\":\"#0\"},\"south\":{\"uv\":[0.5,7,1.5,10],\"texture\":\"#0\"},\"west\":{\"uv\":[3,7,3.5,10],\"texture\":\"#0\"},\"up\":{\"uv\":[3.5,7,4.5,9],\"rotation\":270,\"texture\":\"#0\"},\"down\":{\"uv\":[4,0,4.5,2],\"rotation\":90,\"texture\":\"#0\"}}},{\"from\":[6,0,4],\"to\":[10,6,5],\"rotation\":{\"angle\":0,\"axis\":\"y\",\"origin\":[8,0,4]},\"faces\":{\"north\":{\"uv\":[0.5,7,1.5,10],\"texture\":\"#0\"},\"east\":{\"uv\":[3,7,3.5,10],\"texture\":\"#0\"},\"south\":{\"uv\":[0,0,2,3],\"texture\":\"#missing\"},\"west\":{\"uv\":[0,7,0.5,10],\"texture\":\"#0\"},\"up\":{\"uv\":[3.5,7,4.5,9],\"rotation\":90,\"texture\":\"#0\"},\"down\":{\"uv\":[4,0,4.5,2],\"rotation\":270,\"texture\":\"#0\"}}},{\"from\":[8,6,7.575],\"to\":[12,6.3,8.425],\"faces\":{\"north\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"east\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"south\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"west\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"up\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"down\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"}}},{\"from\":[12,5,7.575],\"to\":[12.3,6.15,8.425],\"faces\":{\"north\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"east\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"south\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"west\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"up\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"},\"down\":{\"uv\":[10,1.5,11.75,1.85],\"texture\":\"#0\"}}}]}";

			api.uploadModel(CosmeticType.HAT, "Fez (JCA Test, Don't Approve)", fezTexture, new JsonParser().parse(fezJason).getAsJsonObject(), 0).get();
		}
	}
}
