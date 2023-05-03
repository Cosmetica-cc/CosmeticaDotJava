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

import cc.cosmetica.api.cosmetic.Cosmetic;

import java.util.Optional;

/**
 * Data class which stores the player's shoulder buddies.
 */
public interface ShoulderBuddies {
	/**
	 * @return the player's left shoulder buddy.
	 */
	Optional<? extends Cosmetic> getLeft();

	/**
	 * @return the player's right shoulder buddy.
	 */
	Optional<? extends Cosmetic> getRight();
}
