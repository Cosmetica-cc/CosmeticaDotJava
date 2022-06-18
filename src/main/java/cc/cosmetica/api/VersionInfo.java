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
 * Object which stores data from a version checker response.
 */
public final class VersionInfo {
	public VersionInfo(boolean needsUpdate, boolean isVital, String minecraftMessage, String plainMessage) {
		this.needsUpdate = needsUpdate;
		this.isVital = isVital;
		this.minecraftMessage = minecraftMessage;
		this.plainMessage = plainMessage;
	}

	private final boolean needsUpdate;
	private final boolean isVital;
	private final String minecraftMessage;
	private final String plainMessage;

	public boolean needsUpdate() {
		return needsUpdate;
	}

	public boolean isVital() {
		return isVital;
	}

	public String minecraftMessage() {
		return minecraftMessage;
	}

	public String plainMessage() {
		return plainMessage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(needsUpdate, minecraftMessage, plainMessage);
	}

	@Override
	public String toString() {
		return "VersionInfo[" +
				"needsUpdate=" + needsUpdate + ", " +
				"isVital=" + isVital + ", " +
				"minecraftMessage=" + minecraftMessage + ", " +
				"plainMessage=" + plainMessage + ']';
	}
}
