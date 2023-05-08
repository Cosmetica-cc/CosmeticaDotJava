/*
 * Copyright 2022, 2023 EyezahMC
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
 * Object which stores data from a version checker response.
 */
public final class VersionInfo {
	public VersionInfo(boolean needsUpdate, boolean isVital, String minecraftMessage, String plainMessage) {
		this(needsUpdate, isVital, minecraftMessage, plainMessage, false);
	}

	public VersionInfo(boolean needsUpdate, boolean isVital, String minecraftMessage, String plainMessage, boolean megaInvasiveTutorial) {
		this.needsUpdate = needsUpdate;
		this.isVital = isVital;
		this.minecraftMessage = minecraftMessage;
		this.plainMessage = plainMessage;
		this.megaInvasiveTutorial = megaInvasiveTutorial;
	}

	private final boolean needsUpdate;
	private final boolean isVital;
	private final boolean megaInvasiveTutorial;
	private final String minecraftMessage;
	private final String plainMessage;

	/**
	 * Gets whether this is an outdated version and should be updated.
	 * @return whether this version is outdated.
	 */
	public boolean needsUpdate() {
		return this.needsUpdate;
	}

	/**
	 * Get whether there is a vital version update.
	 * @return whether there is a vital version update. A version update is vital if features have changed to the point that this version will break.
	 * This message should never be ignored!
	 */
	public boolean isVital() {
		return this.isVital;
	}

	/**
	 * The update message, with colour codes and links to display in-game.
	 * @return the update message to show in minecraft with the mod.
	 */
	public String minecraftMessage() {
		return this.minecraftMessage;
	}

	/**
	 * The update message, with no
	 * @return the plain text version of the update message.
	 */
	public String plainMessage() {
		return this.plainMessage;
	}

	/**
	 * Whether the "mega-invasive tutorial" should be allowed to be shown. Created as a kill switch for the mod feature in case it flops.
	 * @return whether the "mega-invasive tutorial" should be allowed to be shown.
	 */
	public boolean megaInvasiveTutorial() {
		return this.megaInvasiveTutorial;
	}

	@Override
	public String toString() {
		return "VersionInfo[" +
				"needsUpdate=" + needsUpdate + ", " +
				"isVital=" + isVital + ", " +
				"minecraftMessage=" + minecraftMessage + ", " +
				"plainMessage=" + plainMessage + ", " +
				"megaInvasiveTutorial=" + megaInvasiveTutorial + "]";
	}
}
