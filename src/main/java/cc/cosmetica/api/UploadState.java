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

import org.jetbrains.annotations.Nullable;

/**
 * The upload state of a cosmetic.
 */
public enum UploadState {
	/**
	 * Cosmetics that are still waiting to be moderated.
	 */
	PENDING(0),
	/**
	 * Cosmetics that have been approved and may be discovered and equipped by users on the site.
	 */
	APPROVED(1),
	/**
	 * Cosmetics that have been denied by a moderator cannot be discovered and equipped by users on the site.
	 */
	DENIED(2),
	/**
	 * Cosmetics that have been deleted.
	 */
	DELETED(3);

	UploadState(int id) {
		this.id = id;
	}

	private final int id;

	/**
	 * Gets the numerical id of this upload state.
	 * @return the number used to represent this upload state.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the upload state associated with the given id.
	 * @return the upload state associated with the given id, or null if there isn't one.
	 */
	@Nullable
	public static UploadState getById(int id) {
		switch (id) {
		case 0:
			return PENDING;
		case 1:
			return APPROVED;
		case 2:
			return DENIED;
		case 3:
			return DELETED;
		default:
			return null;
		}
	}
}
