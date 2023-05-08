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

import cc.cosmetica.api.User;
import cc.cosmetica.impl.cosmetic.SimpleCosmetic;

import java.util.Optional;

/**
 * Represents any cosmetic. This could be either a cape or model. This is not guaranteed to be an instance of
 * Cape or Model, however, as in some {@linkplain Cosmetic#hasReducedData() reduced-data contexts} a plain old
 * Cosmetic may be returned.
 * @apiNote all classes that extend {@link Cosmetic cosmetic} will be considered "equal" to each other if they have
 * the same id and type.
 */
public interface Cosmetic {
	/**
	 * Gets the type of cosmetic this is.
	 * @return the cosmetic type.
	 */
	CosmeticType<?> getType();

	/**
	 * Gets the owner of this cosmetic. This can be a hyphenless UUID of a player, or "system" (e.g. for the starter capes).
	 * For third party capes, may be empty.
	 * @return the owner of this cosmetic.
	 */
	Optional<User> getOwner();

	/**
	 * Get the upload time of this cosmetic.
	 * @return the UTC unix timestamp, in seconds, at which this model was uploaded.
	 */
	long getUploadTime();

	/**
	 * Gets the name of this cosmetic. May be blank if cosmetica cannot get the name from a third party cosmetic server.
	 * @return the name of this cosmetic.
	 */
	String getName();

	/**
	 * Gets the id of this cosmetic. If not a cosmetica cape, an ID using a format which cannot conflict with
	 * cosmetica's will be generated by the server on the fly using the player UUID and timestamp, so cannot be guaranteed to be the same every time.
	 * @return the id of this cosmetic.
	 * @apiNote With models, it is useful to use this field along with caching to ensure each model is only built once on the application end.
	 */
	String getId();

	/**
	 * Gets the cosmetic server this originates from.
	 * @return the origin of this cape. Whether it be "Cosmetica" or "Optifine," etc...
	 * @since 2.0.0
	 */
	String getOrigin();

	/**
	 * Get the current upload state of this cosmetic.
	 * @return the current upload state of this cosmetic.
	 * @since 2.0.0
	 */
	UploadState getUploadState();

	/**
	 * Get the reason for this cosmetic's upload state. Typically only set for {@link UploadState#DENIED}.
	 * @return the reason for this cosmetic's upload state.
	 * @since 2.0.0
	 */
	String getReason();

	/**
	 * Get whether this custom cosmetic has reduced data. Cosmetics with reduced data
	 * will only have the info required by this interface, minus the username.
	 * @return whether this cosmetic has reduced data.
	 * @since 2.0.0
	 * @apiNote to get a cosmetic will full info given one with reduced info, make a call to
	 * {@link cc.cosmetica.api.CosmeticaAPI#getCosmetic(CosmeticType, String)}
	 */
	boolean hasReducedData();

	/**
	 * Get this custom cosmetic as a {@linkplain Model}.
	 * @return this value, cast to model.
	 * @throws ClassCastException if this is not an instance of {@link Model}
	 * @since 2.0.0
	 */
	default Model getAsModel() throws ClassCastException {
		return (Model) this;
	}

	/**
	 * Get this custom cosmetic as a {@linkplain Cape}.
	 * @return this value, cast to model.
	 * @throws ClassCastException if this is not an instance of {@link Cape}
	 * @since 2.0.0
	 */
	default Cape getAsCape() throws ClassCastException {
		return (Cape) this;
	}

	/**
	 * Create a dummy instance of CustomCosmetic.
	 * @return a new, dummy instance of CustomCosmetic.
	 * @apiNote the id of the cape is guaranteed to be "DUMMY"
	 */
	static Cosmetic createDummy() {
		return SimpleCosmetic.createDummy();
	}
}
