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

package cc.cosmetica.api.cosmetic;

import cc.cosmetica.api.CosmeticaAPI;

import java.util.UUID;

/**
 * Represents the data of a cosmetic owned by a player, retrieved from {@link CosmeticaAPI#getCosmeticsOwnedBy(UUID, String)}. The data here is slightly different to that which you get from other endpoints.
 */
public interface OwnedCosmetic {
	/**
	 * Gets the type of cosmetic this is.
	 * @return the cosmetic type.
	 */
	CosmeticType<?> getType();

	/**
	 * Gets the cosmetic server this originates from.
	 * @return the origin of this cape. Whether it be "Cosmetica" or "Optifine," etc...
	 */
	String getOrigin();

	/**
	 * Gets the name of this cosmetic.
	 * @return the name of this cosmetic.
	 */
	String getName();

	/**
	 * Gets the id of this cosmetic.
	 * @return the id of this cosmetic.
	 */
	String getId();

	/**
	 * Get the upload time of this cosmetic.
	 * @return the UTC unix timestamp, in seconds, at which this model was uploaded.
	 */
	long getUploadTime();

	/**
	 * Gets how many users currently have this cosmetic equipped.
	 * @return the number of users with this cosmetic currently equipped, as an int.
	 */
	int getUsers();

	/**
	 * Gets the current upload state of this cosmetic.
	 * @return the upload state of this cosmetic.
	 */
	UploadState getUploadState();
}
