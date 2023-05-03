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
 * The upload state of a cosmetic.
 */
public enum UploadState {
	/**
	 * Used when the numerical cosmetic status is an identifier unknown to the program. This may be intentional
	 * (such as capes from third-party services), or due to this instance not recognising the id of a status.<br/>
	 * For example, if a new status "12: foo" were to be added to the protocol and sent over, this value would be used.
	 * @since 2.0.0
	 * @apiNote Make sure you are using the latest version of CosmeticaDotJava so that newer status ids are fully
	 * represented!
	 */
	UNKNOWN(-1),
	/**
	 * Cosmetics that have been uploaded and are waiting for moderation decision.
	 */
	PENDING(0),
	/**
	 * Cosmetics that have been approved and may be discovered and equipped by users on the site.
	 */
	APPROVED(1),
	/**
	 * Cosmetics that have been denied by a moderator cannot be discovered and equipped by users on the site.
	 * A cosmetic with this status should also have a non-empty reason.
	 */
	DENIED(2),
	/**
	 * Cosmetics that have been deleted by their owners or by a moderator.
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
	 * @return the upload state associated with the given id.
	 */
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
			return UNKNOWN;
		}
	}
}
