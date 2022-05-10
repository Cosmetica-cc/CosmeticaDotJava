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

import java.io.IOException;

/**
 * Exception for http error codes.
 * @apiNote Not called "http exception" because that's already in use by apache httpclient.
 */
public class HttpNotOkException extends IOException {
	public HttpNotOkException(String url, int errorCode) {
		super("Received error code " + errorCode + " from URL " + url);

		this.url = url;
		this.errorCode = errorCode;
	}

	public final String url;
	public final int errorCode;
}
