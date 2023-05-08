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

package cc.cosmetica.api.settings;

/**
 * The settings for a cape server.
 */
public class CapeServer {
	public CapeServer(String name, String warning, int checkOrder, CapeDisplay display) {
		this.name = name;
		this.warning = warning;
		this.checkOrder = checkOrder;
		this.display = display;
	}

	private final String name;
	private final String warning;
	private final int checkOrder;
	private final CapeDisplay display;

	/**
	 * @return the decorated name of this cape server.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the warning for enabling this or an empty string to display to users. The actual message should be chosen by the implementation.
	 * @return the warning to display the user for enabling this setting. Will be undecorated, or an empty string if there is no warning.
	 */
	public String getWarning() {
		return this.warning;
	}

	public int getCheckOrder() {
		return this.checkOrder;
	}

	public CapeDisplay getDisplay() {
		return this.display;
	}
}
