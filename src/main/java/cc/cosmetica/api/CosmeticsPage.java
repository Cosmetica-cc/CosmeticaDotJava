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

import java.util.List;
import java.util.Objects;

/**
 * A page of cosmetics on the website.
 * @param <T> the type of cosmetic found on this page. Could be {@link Cosmetic} if a general page.
 */
public final class CosmeticsPage<T extends Cosmetic> {
	private final List<T> cosmetics;
	private final boolean nextPage;

	public CosmeticsPage(List<T> cosmetics, boolean nextPage) {
		this.cosmetics = cosmetics;
		this.nextPage = nextPage;
	}

	public List<T> getCosmetics() {
		return cosmetics;
	}

	public boolean hasNextPage() {
		return nextPage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		CosmeticsPage that = (CosmeticsPage) obj;
		return Objects.equals(this.cosmetics, that.cosmetics) &&
				this.nextPage == that.nextPage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cosmetics, nextPage);
	}

	@Override
	public String toString() {
		return "CosmeticsPage[" +
				"cosmetics=" + cosmetics + ", " +
				"nextPage=" + nextPage + ']';
	}

}
