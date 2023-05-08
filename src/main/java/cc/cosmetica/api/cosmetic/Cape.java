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

package cc.cosmetica.api.cosmetic;

import cc.cosmetica.impl.CosmeticFetcher;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

/**
 * Represents the data of a cape served by the cosmetica servers.
 */
public interface Cape extends Cosmetic {
	/**
	 * Get the cape base64 image string. May be different dimensions depending on how many frames it has. Each frame has a 2:1 width:height aspect ratio and they stack vertically.
	 * @return the image, in base64 format
	 */
	String getImage();

	/**
	 * @return the delay between each frame of this cape, in ms. Will be 0 if static.
	 */
	int getFrameDelay();

	@Override
	default OptionalInt getExtraInfo() {
		return OptionalInt.of(this.getFrameDelay());
	}

	/**
	 * @return whether this cape is an animated cape.
	 */
	default boolean isAnimated() {
		return this.getFrameDelay() > 0;
	}

	/**
	 * @return whether the user's cape, if from an alternative cape server, has been replaced with a cosmetica cape, in accordance with the cape settings of the token this cape was obtained with.
	 */
	boolean isCosmeticaAlternative();

	/**
	 * Makes an api request to fetch cape data from cosmetica.
	 * @param id the id of the cape to request
	 * @return an object containing information on the cape. Null if there is no cape for the given id.
	 */
	@Nullable
	static Cape fetch(String id) {
		return CosmeticFetcher.getCape(id);
	}
}
