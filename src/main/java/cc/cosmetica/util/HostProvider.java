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

/**
 * Provides a host to use based on the configured settings, parameters, and purpose.
 * @apiNote the API will ALWAYS use HTTPS for connections that use your master token.
 * The only endpoints that don't use your master token are non-info-sensitive ones such as getting another player's cosmetics.
 */
public class HostProvider implements Cloneable {
	public HostProvider(String url, boolean forceHttps) {
		boolean httpsGiven = url.startsWith("https");

		if (httpsGiven) {
			this.httpUrl = "http" + url.substring(5);
			this.httpsUrl = this.url = url;
		} else {
			this.httpsUrl = "https" + url.substring(4);
			this.httpUrl = this.url = url;
		}

		this.forceHttps = forceHttps;
	}

	private final String url;
	private final String httpsUrl;
	private final String httpUrl;

	private boolean forceHttps;

	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	// ===================== //
	// URLs for Transactions //
	// ===================== //

	/**
	 * Get the url to use for most interactions.
	 * @return the url to use for most interactions.
	 */
	public String getSecureUrl() {
		return this.forceHttps ? this.httpsUrl : this.url;
	}

	/**
	 * Get the url to use for interactions that don't need much security and can thus sacrifice it for speed.
	 * @return the url to use for these interactions.
	 */
	public String getFastInsecureUrl() {
		return this.forceHttps ? this.httpsUrl : this.httpUrl;
	}

	// ===================== //
	//   Getters and Stuff   //
	// ===================== //

	/**
	 * Get whether this host provider forces https.
	 * @return whether this host provider forces https.
	 */
	public boolean isForceHttps() {
		return this.forceHttps;
	}

	@Override
	public HostProvider clone() {
		return new HostProvider(this.url, this.forceHttps);
	}
}
