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
 * A custom model which is stored on the cosmetica servers. Most models are of this type aside - the only exception a very few number of "built-in" models to cosmetica used in region specific effects.
 */
public interface CustomModel extends Model, CustomCosmetic {
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
}
