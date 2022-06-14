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
 * A bounding box constructed from 2 corners. Objects of this type are required to have {@code x0 <= x1}, {@code y0 <= y1}, {@code z0 <= z1}.
 */
public final class Box {
	public Box(double x0, double y0, double z0,
			   double x1, double y1, double z1) {
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
	}

	private final double x0;
	private final double y0;
	private final double z0;
	private final double x1;
	private final double y1;
	private final double z1;

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

	public double x0() {
		return x0;
	}

	public double y0() {
		return y0;
	}

	public double z0() {
		return z0;
	}

	public double x1() {
		return x1;
	}

	public double y1() {
		return y1;
	}

	public double z1() {
		return z1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Box) obj;
		return Double.doubleToLongBits(this.x0) == Double.doubleToLongBits(that.x0) &&
				Double.doubleToLongBits(this.y0) == Double.doubleToLongBits(that.y0) &&
				Double.doubleToLongBits(this.z0) == Double.doubleToLongBits(that.z0) &&
				Double.doubleToLongBits(this.x1) == Double.doubleToLongBits(that.x1) &&
				Double.doubleToLongBits(this.y1) == Double.doubleToLongBits(that.y1) &&
				Double.doubleToLongBits(this.z1) == Double.doubleToLongBits(that.z1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x0, y0, z0, x1, y1, z1);
	}

	@Override
	public String toString() {
		return "Box[" +
				"x0=" + x0 + ", " +
				"y0=" + y0 + ", " +
				"z0=" + z0 + ", " +
				"x1=" + x1 + ", " +
				"y1=" + y1 + ", " +
				"z1=" + z1 + ']';
	}

}
