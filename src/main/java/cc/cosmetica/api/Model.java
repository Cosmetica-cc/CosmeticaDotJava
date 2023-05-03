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

import cc.cosmetica.impl.CosmeticFetcher;
import org.jetbrains.annotations.Nullable;

/**
 * A cosmetica model. Can be a built-in model, or a custom model. See {@link Model#isBuiltin()} for more details.
 */
public interface Model extends Cosmetic {
	/**
	 * @return the bounding box of this model.
	 */
	Box getBoundingBox();

	/**
	 * @return the model json string associated with the custom model. Follows the minecraft block model format.
	 */
	String getModel();

	/**
	 * @return the texture, in base64 format. The texture will be a tilesheet of square textures representing each frame.
	 */
	String getTexture();

	/**
	 * @return whether this model uses UV rotations.
	 * @apiNote useful for applications which cannot, for whatever reason, support UV rotations in textures, or need special handling thereof.
	 */
	boolean usesUVRotations();

	/**
	 * Retrieves the rendering flags of this model. The exact meaning depends on the type of cosmetic, and can be found as constant fields in this class.
	 * @return the rendering flags associated with this model.
	 */
	int flags();

	/**
	 * @return the delay between each frame of this model's texture, in ms. Will be 0 if static.
	 * @implNote Equivalent to {@code 50 * ((flags() >> 4) & 0x1F)}
	 */
	int getFrameDelay();

	/**
	 * @return whether this is a built in model to cosmetica (e.g. for region specific effects), as opposed to a custom model stored on the server.
	 */
	boolean isBuiltin();

	/**
	 * Makes an api request to fetch model data from cosmetica.
	 * @param type the type of model to request.
	 * @param id the id of the model to request
	 * @return an object containing information on the model. Null if there is no model for the given id.
	 */
	@Nullable
	static Model fetch(CosmeticType<Model> type, String id) {
		return CosmeticFetcher.getModel(type, id);
	}

	/**
	 * Makes an api request to fetch hat data from cosmetica.
	 * @param id the id of the hat to request
	 * @return an object containing information on the hat. Null if there is no hat for the given id.
	 */
	@Nullable
	static Model fetchHat(String id) {
		return CosmeticFetcher.getModel(CosmeticType.HAT, id);
	}

	/**
	 * Makes an api request to fetch shoulder buddy data from cosmetica.
	 * @param id the id of the shoulder buddy to request
	 * @return an object containing information on the shoulder buddy. Null if there is no shoulder buddy for the given id.
	 */
	@Nullable
	static Model fetchShoulderBuddy(String id) {
		return CosmeticFetcher.getModel(CosmeticType.SHOULDER_BUDDY, id);
	}

	/**
	 * Makes an api request to fetch back bling data from cosmetica.
	 * @param id the id of the hat to request
	 * @return an object containing information on the back bling. Null if there is no back bling for the given id.
	 */
	@Nullable
	static Model fetchBackBling(String id) {
		return CosmeticFetcher.getModel(CosmeticType.BACK_BLING, id);
	}

	// hat flags
	/**
	 * Flag for whether this hat should be hidden when a helmet being worn.
	 */
	int SHOW_HAT_WITH_HELMET = 0x1;

	/**
	 * Flag for whether to lock the hat orientation to the torso.
	 */
	int LOCK_HAT_ORIENTATION = 0x2;

	// shoulder buddy flags
	/**
	 * Flag for whether to lock the shoulder buddy orientation to the torso.
	 */
	int LOCK_SHOULDER_BUDDY_ORIENTATION = 0x1;

	/**
	 * Flag for whether the shoulder buddy should mirror when being used on the right shoulder/arm instead of the left. If this is set, the shoulder buddy will not be mirrored.
	 */
	int DONT_MIRROR_SHOULDER_BUDDY = 0x2;

	/**
	 * Flag for whether this shoulder buddy should be shown when a parrot is on its shoulder.
	 */
	int SHOW_SHOULDER_BUDDY_WITH_PARROT = 0x4;

	// back bling flags
	/**
	 * Flag for whether this back bling should show when the player is wearing a chestplate.
	 */
	int SHOW_BACK_BLING_WITH_CHESTPLATE = 0x1;

	/**
	 * Flag for whether this back bling should show when the player is wearing a cape or elytra.
	 */
	int SHOW_BACK_BLING_WITH_CAPE = 0x2;
}
