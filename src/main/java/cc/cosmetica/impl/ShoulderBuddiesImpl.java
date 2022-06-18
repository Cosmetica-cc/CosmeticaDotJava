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

package cc.cosmetica.impl;

import cc.cosmetica.api.Model;
import cc.cosmetica.api.ShoulderBuddies;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class ShoulderBuddiesImpl implements ShoulderBuddies {
	public ShoulderBuddiesImpl(Optional<Model> left, Optional<Model> right) {
		this.left = left;
		this.right = right;
	}

	private final Optional<Model> left;
	private final Optional<Model> right;

	/**
	 * @return the player's left shoulder buddy.
	 */
	@Nullable
	public Optional<Model> getLeft() {
		return this.left;
	}

	/**
	 * @return the player's right shoulder buddy.
	 */
	@Nullable
	public Optional<Model> getRight() {
		return this.right;
	}
}
