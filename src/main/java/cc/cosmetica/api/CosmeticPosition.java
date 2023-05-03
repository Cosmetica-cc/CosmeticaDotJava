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

import cc.cosmetica.api.cosmetic.CosmeticType;

/**
 * A position at which a cosmetic can be placed on a player. Used in {@link CosmeticaAPI#setCosmetic(CosmeticPosition, String)}.
 */
public enum CosmeticPosition {
	CAPE(CosmeticType.CAPE, "cape"),
	HAT(CosmeticType.HAT, "hat"),
	/**
	 * The second hat, if equipped. Note that if you set the cosmetic at this position while the player has no hats,
	 * or remove the cosmetic at the hat position while the player is wearing two hats, the second hat will be moved to the main hat slot to ensure that position is always used for when the player has a single hat equipped.
	 */
	SECOND_HAT(CosmeticType.HAT, "hat2"),
	LEFT_SHOULDER_BUDDY(CosmeticType.SHOULDER_BUDDY, "leftshoulderbuddy"),
	RIGHT_SHOULDER_BUDDY(CosmeticType.SHOULDER_BUDDY, "rightshoulderbuddy"),
	BACK_BLING(CosmeticType.BACK_BLING, "backbling");

	CosmeticPosition(CosmeticType<?> type, String urlString) {
		this.type = type;
		this.urlString = urlString;
	}

	private final CosmeticType<?> type;
	private final String urlString;

	/**
	 * @return the type of cosmetic that can be placed at this position.
	 */
	public CosmeticType<?> getType() {
		return this.type;
	}

	/**
	 * @return the string of this cosmetic position as used in the type parameter for /client/setcosmetic.
	 */
	public String getUrlString() {
		return this.urlString;
	}
}
