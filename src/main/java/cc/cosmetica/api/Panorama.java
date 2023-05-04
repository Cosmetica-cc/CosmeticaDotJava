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

import java.util.Objects;

/**
 * Represents a panorama background on the cosmetica website.
 */
public final class Panorama {
	/**
	 */
	public Panorama(int id, String name, boolean free) {
		this.id = id;
		this.name = name;
		this.free = free;
	}

	private final int id;
	private final String name;
	private final boolean free;

	/**
	 * @return the numerical id of the panorama.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name of the panorama.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return whether this panorama can be used by all users. Some panoramas are reserved to staff, donators, etc.
	 */
	public boolean isFree() {
		return free;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		Panorama that = (Panorama) obj;
		return this.id == that.id &&
				Objects.equals(this.name, that.name) &&
				this.free == that.free;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, free);
	}

	@Override
	public String toString() {
		return "Panorama[" +
				"id=" + id + ", " +
				"name=" + name + ", " +
				"free=" + free + ']';
	}

}
