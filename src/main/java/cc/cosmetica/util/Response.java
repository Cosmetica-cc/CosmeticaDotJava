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

package cc.cosmetica.util;

import cc.cosmetica.api.FatalServerErrorException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class Response implements Closeable {
	private Response(CloseableHttpClient client, CloseableHttpResponse response) {
		this.client = client;
		this.response = response;
		this.status = this.response.getStatusLine();
	}

	private final CloseableHttpClient client;
	private final CloseableHttpResponse response;
	private final StatusLine status;

	public StatusLine getStatus() {
		return this.status;
	}

	public int getStatusCode() {
		return this.status.getStatusCode();
	}

	public OptionalInt getError() {
		int code = this.getStatusCode();

		if (code >= 200 && code < 300) {
			return OptionalInt.empty();
		} else {
			return OptionalInt.of(code);
		}
	}

	@Nullable
	public HttpEntity getEntity() {
		return this.response.getEntity();
	}

	public String getAsString() throws IOException {
		HttpEntity entity = this.getEntity();
		return entity == null ? "" : EntityUtils.toString(entity, StandardCharsets.UTF_8);
	}

	public byte[] getAsByteArray() throws IOException {
		HttpEntity entity = this.getEntity();
		return entity == null ? new byte[0] : EntityUtils.toByteArray(this.getEntity());
	}

	public JsonObject getAsJson() throws IOException, JsonParseException {
		String s = EntityUtils.toString(this.getEntity(), StandardCharsets.UTF_8).trim();
		return JsonParser.parseString(s).getAsJsonObject();
	}

	public JsonArray getAsJsonArray() throws IOException, JsonParseException {
		String s = EntityUtils.toString(this.getEntity(), StandardCharsets.UTF_8).trim();
		return JsonParser.parseString(s).getAsJsonArray();
	}

	public JsonElement getAsJsonElement() throws IOException, JsonParseException {
		String s = EntityUtils.toString(this.getEntity(), StandardCharsets.UTF_8).trim();
		return JsonParser.parseString(s);
	}

	@Override
	public void close() throws IOException {
		this.response.close();
		this.client.close();
	}

	public static Response get(String request) throws ParseException, IOException, FatalServerErrorException {
		return get(SafeURL.direct(request));
	}

	/**
	 * @apiNote cosmetica api will include the safe url in an {@link FatalServerErrorException}.
	 */
	public static Response get(SafeURL request) throws ParseException, IOException, FatalServerErrorException {
		return _get(request.url()).testForFatalError(request);
	}

	private static Response _get(String request) throws ParseException, IOException {
		final int timeout = 15 * 1000;

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();

		CloseableHttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.build();

		final HttpGet get = new HttpGet(request);

		CloseableHttpResponse response = client.execute(get);
		return new Response(client, response);
	}

	public static PostBuilder post(SafeURL request) {
		return new FormPostBuilder(request);
	}

	public static PostBuilder post(String request) {
		return new FormPostBuilder(SafeURL.direct(request));
	}

	public static PostBuilder postJson(SafeURL request) {
		return new JsonPostBuilder(request);
	}

	public static PostBuilder postJson(String request) {
		return new JsonPostBuilder(SafeURL.direct(request));
	}

	private Response testForFatalError(SafeURL safeUrl) throws FatalServerErrorException, IOException {
		final int code = this.getStatusCode();

		if (code >= 500) {
			try {
				JsonParser.parseString(this.getAsString());
			}
			catch (JsonParseException e) {
				// probably xml or something we can't handle. i.e. not an actual API response with 500
				throw new FatalServerErrorException(safeUrl.safeUrl(), code);
			}
		}

		return this;
	}

	public static abstract class PostBuilder {
		private PostBuilder(SafeURL url) {
			this.url = url;
		}

		private final SafeURL url;
		private String accepts = "application/json";

		public Response submit() throws ParseException, IOException, FatalServerErrorException {
			final int timeout = 15 * 1000;

			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout)
					.setSocketTimeout(timeout)
					.build();

			CloseableHttpClient client = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig)
					.build();

			final HttpPost post = new HttpPost(this.url.url());
			post.setEntity(getEntity());

			CloseableHttpResponse response = client.execute(post);

			// validate
			return new Response(client, response).testForFatalError(this.url);
		}

		abstract public PostBuilder set(String key, String value);

		public PostBuilder set(String key, int value) {
			return this.set(key, String.valueOf(value));
		}

		abstract protected HttpEntity getEntity();
	}

	private static class FormPostBuilder extends PostBuilder {
		private FormPostBuilder(SafeURL url) {
			super(url);
		}

		private final List<NameValuePair> form = new ArrayList<>();

		@Override
		public PostBuilder set(String key, String value) {
			this.form.add(new BasicNameValuePair(key, value));
			return this;
		}

		@Override
		public HttpEntity getEntity() {
			return new UrlEncodedFormEntity(this.form, Consts.UTF_8);
		}
	}

	private static class JsonPostBuilder extends PostBuilder {
		private JsonPostBuilder(SafeURL url) {
			super(url);
		}

		private final StringBuilder entity = new StringBuilder("{");
		private boolean started = false;

		@Override
		public PostBuilder set(String key, String value) {
			if (this.started) {
				this.entity.append(',');
			}
			else {
				this.started = true;
			}

			this.entity.append('"').append(key).append("\":\"").append(value).append('"');
			return this;
		}

		@Override
		public HttpEntity getEntity() {
			return new StringEntity(this.entity.append('}').toString(), ContentType.APPLICATION_JSON);
		}
	}
}