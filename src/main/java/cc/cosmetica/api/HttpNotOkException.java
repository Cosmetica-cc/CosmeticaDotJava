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
