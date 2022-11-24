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

/**
 * Represents a cosmetic stored on cosmetica servers. This could be either a cape or model.
 */
public interface CustomCosmetic {
	/**
	 * Gets the type of cosmetic this is.
	 * @return the cosmetic type.
	 */
	CosmeticType<?> getType();

	/**
	 * Gets the owner of this cosmetic.
	 * @return the owner of this cosmetic. Can be a hyphenless UUID of a player, or "system" (e.g. for the starter capes).
	 */
	User getOwner();

	/**
	 * Get the upload time of this cosmetic.
	 * @return the UTC unix timestamp, in seconds, at which this model was uploaded.
	 */
	long getUploadTime();

	/**
	 * Gets the name of this cosmetic.
	 * @return the name of this cosmetic.
	 */
	String getName();

	/**
	 * Gets the id of this cosmetic.
	 * @return the id of this cosmetic.
	 * @apiNote It is useful to use this field along with caching to ensure each model is only built once on the application end.
	 */
	String getId();
}
