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
public interface Model extends CustomCosmetic {
	/**
	 * @remturn the id of this model.
	 * @apiNote It is useful to use this field along with caching to ensure each model is only built once on the application end.
	 */
	String getId();

	/**
	 * @return the bounding box of this model.
	 */
	Box getBoundingBox();

	/**
	 * @return the model json string associated with the custom model. Follows the minecraft block model format.
	 */
	String getModel();

	/**
	 * @return the texture, in base64 format.
	 * @implNote as of the time of building this library, this texture is guaranteed to be 32x32, however this is subject to change in the future.
	 */
	String getTexture();

	/**
	 * @return whether this model uses UV rotations.
	 * @apiNote useful for applications which cannot, for whatever reason, support UV rotations in textures, or need special handling thereof.
	 */
	boolean usesUVRotations();

	/**
	 * Retrieves the rendering flags of this model. The exact meaning depends on the type of cosmetic. As of the time of this library build, the current options are:
	 * <br/><br/>
	 * <h3>Hats</h3>
	 * <ul>
	 *     <li>0x1: Show Through Helmet</li>
	 * </ul>
	 * <br/>
	 * <h3>Shoulder Buddies</h3>
	 * 	 <ul>
	 * 	     <li>0x1: Lock to body orientation</li>
	 * 	 </ul>
	 * @return the rendering flags associated with this model.
	 */
	int flags();

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
	int HIDE_HAT_UNDER_HELMET = 0x1;
	int LOCK_HAT_ORIENTATION = 0x2;
	// shoulder buddies
	int LOCK_SHOULDER_BUDDY_ORIENTATION = 0x1;
	int MIRROR_SHOULDER_BUDDY = 0x2;
}
