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
 * How the cosmetics from a cape service are handled by cosmetica for this user.
 */
public enum CapeDisplay {
	REPLACE(0),
	HIDE(1),
	SHOW(2);

	CapeDisplay(int id) {
		this.id = id;
	}

	public final int id;

	private static final CapeDisplay[] BY_ID = new CapeDisplay[CapeDisplay.values().length]; // because apparently using ordinal is small brain

	public static CapeDisplay byId(int id) {
		return BY_ID[id];
	}

	static {
		for (CapeDisplay entry : CapeDisplay.values()) {
			BY_ID[entry.id] = entry;
		}
	}
}
