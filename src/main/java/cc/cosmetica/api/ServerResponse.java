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

import cc.cosmetica.util.SafeURL;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An immutable object of a value or an error, for use in passing API responses. Basically Optional on steroids.
 * Common errors are {@link HttpNotOkException} for a non-ok http response, {@link CosmeticaAPIException} for an error response from the cosmetica api, and {@link java.io.IOException} for various I/O exceptions.
 * Keep in mind HttpNotOkException extends IOException.
 */
public class ServerResponse<T> {
	public ServerResponse(T t, SafeURL url) throws IllegalArgumentException {
		if (t == null) throw new IllegalArgumentException("Object t cannot be null.");
		this.value = t;
		this.exception = null;
		this.url = url;
	}

	public ServerResponse(Exception e, SafeURL url) throws IllegalArgumentException {
		if (e == null) throw new IllegalArgumentException("Exception e cannot be null.");
		this.value = null;
		this.exception = e;
		this.url = url;
	}

	@Nullable
	private final T value;
	@Nullable
	private final Exception exception;
	private final SafeURL url;

	/**
	 * @return the url that was contacted to receive this response.
	 */
	public String url() {
		return this.url.safeUrl();
	}

	/**
	 * Tries to get the value stored. If that value is an exception, it will be thrown.
	 * @throws Exception if the object stores an exception. The exception thrown is the exact same as the one stored.
	 */
	public T unwrap() throws Exception {
		if (this.value == null) throw this.exception;
		return this.value;
	}

	// TODO surely we should only keep one of the below.

	/**
	 * Tries to get the value stored. If that value is an exception, it will be thrown wrapped in an IllegalStateException.
	 * @throws IllegalStateException if the object stores an exception. The exception thrown wraps one stored.
	 */
	public T get() throws IllegalStateException {
		if (this.value == null) throw new IllegalStateException("There was an error while/from contacting " + this.url.safeUrl() + ": ", this.exception);
		return this.value;
	}

	/**
	 * Returns the value stored, if present. Otherwise throws a null pointer exception with a message containing the message of the underlying exception.
	 * @throws NullPointerException of there is no value present
	 */
	public T getValue() throws NullPointerException {
		if (this.value == null) throw new NullPointerException("The server response contains an error! " + this.exception.getMessage());
		return this.value;
	}

	@Nullable
	public T getOrNull() {
		return this.value;
	}

	@Nullable
	public Exception getException() {
		return this.exception;
	}

	/**
	 * @param errorResolver the function to handle errors and resolve a value.
	 * @return the resolved value.
	 * @apiNote nullable if and only if the error resolver passed can return null.
	 */
	@Nullable
	public T resolve(Function<Exception, @Nullable T> errorResolver) {
		if (this.value == null) return errorResolver.apply(this.exception);
		return this.value;
	}

	/**
	 * @param fallback the value to return if there was an error.
	 * @return the resolved value.
	 * @apiNote nullable if and only if the error resolver passed can return null.
	 */
	@Nullable
	public T or(T fallback) {
		if (this.value == null) return fallback;
		return this.value;
	}

	/**
	 * Retrieves the value stored. If there is an error instead, it logs the exception and returns the fallback value.
	 *
	 * @param fallback the value to return if there was an error.
	 * @return the resolved value.
	 * @apiNote nullable if and only if the error resolver passed can return null.
	 */
	@Nullable
	public T orLogAndUse(T fallback) {
		if (this.value == null) {
			this.exception.printStackTrace();
			return fallback;
		}

		return this.value;
	}

	/**
	 * Basically your generic "isPresent" optional function.
	 * @return whether there is a value stored in here (as opposed to an error).
	 */
	public boolean isSuccessful() {
		return this.value != null;
	}

	/**
	 * Runs the given callback with the value if successful.
	 */
	public void ifSuccessful(Consumer<T> callback) {
		if (this.value != null) {
			callback.accept(this.value);
		}
	}

	/**
	 * Runs the given callback with the error if there was an error.
	 */
	public void ifError(Consumer<Exception> callback) {
		if (this.value == null) {
			callback.accept(this.exception);
		}
	}

	/**
	 * Runs the given callback with the value if successful, otherwise runs the "or else" branch with the error.
	 */
	public void ifSuccessfulOrElse(Consumer<T> successCallback, Consumer<Exception> errorCallback) {
		if (this.value == null) {
			errorCallback.accept(this.exception);
		} else {
			successCallback.accept(this.value);
		}
	}
}
