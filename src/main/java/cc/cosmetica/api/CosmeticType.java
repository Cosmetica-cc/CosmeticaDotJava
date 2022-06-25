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

package cc.cosmetica.api;

import java.util.Optional;

/**
 * A set of types of cosmetics.
 */
public class CosmeticType<T extends CustomCosmetic> {
	private CosmeticType(String urlstring) {
		this.urlString = urlstring;
	}

	private final String urlString;

	/**
	 * @return the string of how this type of cosmetic is encoded into urls.
	 */
	public String getUrlString() {
		return this.urlString;
	}

	public static final CosmeticType<CustomCape> CAPE = new CosmeticType<>("cape");
	public static final CosmeticType<Model> HAT = new CosmeticType<>("hat");
	public static final CosmeticType<Model> SHOULDER_BUDDY = new CosmeticType<>("shoulderbuddy");
	public static final CosmeticType<Model> BACK_BLING = new CosmeticType<>("backbling");

	/**
	 * Gets the cosmetic type instance from the case-sensitive url string.
	 * @param urlstring the case-sensitive string associated with this cosmetic in api urls.
	 * @return the type associated with this string. Returns Optional.empty() if none.
	 */
	public static Optional<CosmeticType<?>> fromUrlString(String urlstring) {
		switch (urlstring) {
		case "cape":
			return Optional.of(CAPE);
		case "hat":
			return Optional.of(HAT);
		case "shoulderbuddy":
			return Optional.of(SHOULDER_BUDDY);
		default:
			return Optional.empty();
		}
	}

	/**
	 * Gets the cosmetic type instance from the case-sensitive type string.
	 * @param typeString the case-sensitive string associated with this cosmetic in api responses.
	 * @return the type associated with this string. Returns Optional.empty() if none.
	 */
	public static Optional<CosmeticType<?>> fromTypeString(String typeString) {
		switch (typeString) {
		case "Cape":
			return Optional.of(CAPE);
		case "Hat":
			return Optional.of(HAT);
		case "Shoulder Buddy":
			return Optional.of(SHOULDER_BUDDY);
		default:
			return Optional.empty();
		}
	}
}
