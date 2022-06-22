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

import cc.cosmetica.impl.DummyUserInfo;

import java.util.List;
import java.util.Optional;

/**
 * An object containing the response of a user info get request.
 */
public interface UserInfo {
	/**
	 * @return the skin of this user, in base64 format.
	 */
	String getSkin();

	/**
	 * @return whether the player model is slim.
	 */
	boolean isSlim();

	/**
	 * @return the user's lore, i.e. the text that should be displayed below their name. This may start with a minecraft colour code.
	 */
	String getLore();

	/**
	 * @return the platform this user is registered on. Can be "java" or "bedrock".
	 */
	String getPlatform();

	/**
	 * @return the role of the user on the Cosmetica platform. For example, "admin" or "default". If the user has not used cosmetica before, their role will be "none".
	 */
	String getRole();

	/**
	 * @return whether the user should be rendered upside down in game due to region specific effects.
	 */
	boolean isUpsideDown();

	/**
	 * @return A prefix to the user's name.
	 */
	String getPrefix();

	/**
	 * @return a suffix to the user's name.
	 */
	String getSuffix();

	/**
	 * @return a list of the hats worn by this user.
	 */
	List<Model> getHats();

	/**
	 * @return the shoulder buddies worn by this user.
	 */
	Optional<ShoulderBuddies> getShoulderBuddies();

	/**
	 * @return the back bling worn by this user.
	 */
	Optional<Model> getBackBling();

	/**
	 * @return the cape worn by this user.
	 */
	Optional<Cape> getCape();

	/**
	 * A {@link UserInfo} instance with dummy values for everything which can be used as a placeholder.
	 */
	UserInfo DUMMY = new DummyUserInfo();
}
