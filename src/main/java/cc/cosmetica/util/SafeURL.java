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

package cc.cosmetica.util;

import java.util.Objects;

/**
 * Safe URL thing for keeping tokens safe.
 */
public final class SafeURL {
	private final String url;
	private final String safeUrl;

	private SafeURL(String url, String safeUrl) {
		this.url = url;
		this.safeUrl = safeUrl;
	}

	@Override
	public String toString() {
		return "SafeURL{" + this.safeUrl + "}";
	}

	public String url() {
		return url;
	}

	public String safeUrl() {
		return safeUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		SafeURL that = (SafeURL) obj;
		return Objects.equals(this.url, that.url) &&
				Objects.equals(this.safeUrl, that.safeUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, safeUrl);
	}

	public static SafeURL of(String baseUrl, String token) {
		return new SafeURL(baseUrl + (baseUrl.contains("?") ? "&" : "?") + "token=" + token, baseUrl);
	}

	public static SafeURL of(String baseUrl) {
		return new SafeURL(baseUrl + (baseUrl.contains("?") ? "&" : "?") + "token=", baseUrl);
	}

	public static SafeURL direct(String safeRequest) {
		return new SafeURL(safeRequest, safeRequest);
	}

	public static SafeURL dummy() {
		return direct("https://example.com/no-request-made");
	}
}
