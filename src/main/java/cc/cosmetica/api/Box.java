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
 * A bounding box constructed from 2 corners. Objects of this type are required to have {@code x0 <= x1}, {@code y0 <= y1}, {@code z0 <= z1}.
 */
public record Box(double x0, double y0, double z0,
		 double x1, double y1, double z1) {
	/**
	 * Checks whether the given coordinate is inside this bounding box.
	 * @param x the x position of the coordinate.
	 * @param y the y position of the coordinate.
	 * @param z the z position of the coordinate.
	 * @return whether the given coordinate is inside this bounding box.
	 */
	public boolean isInBox(double x, double y, double z) {
		return this.x0 <= x && x <= this.x1
				&& this.y0 <= y && y <= this.y1
				&& this.z0 <= z && z <= this.z1;
	}

	/**
	 * @return the size in the x direction of this box.
	 */
	public double getWidth() {
		return this.x1 - this.x0;
	}

	/**
	 * @return the size in the y direction of this box.
	 */
	public double getHeight() {
		return this.y1 - this.y0;
	}

	/**
	 * @return the size in the z direction of this box.
	 */
	public double getDepth() {
		return this.z1 - this.z0;
	}
}
