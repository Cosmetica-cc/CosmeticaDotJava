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
 * Exception thrown when an API request returns a 5XX error with no attached JSON response, i.e. a fatal internal server error that caused the server to not respond properly.
 */
public class FatalServerErrorException extends RuntimeException {
	public FatalServerErrorException(String url, int errorCode) {
		super("Received fatal internal server error (code " + errorCode + ") from URL " + url);

		this.url = url;
		this.errorCode = errorCode;
	}

	public final String url;
	public final int errorCode;
}
