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
 * A cosmetica model. Can be a built-in model, or a custom model. See {@link Model#isBuiltin()} for more details.
 */
public interface Model {
	/**
	 * @return the id of this model.
	 * @apiNote It is useful to use this field along with caching to ensure each model is only built once on the application end.
	 */
	String getId();

	/**
	 * @return the bounding box of this model.
	 */
	Box getBoundingBox();

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
}
