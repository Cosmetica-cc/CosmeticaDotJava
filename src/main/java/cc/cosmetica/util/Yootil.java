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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * General utilities used by the implementation.
 */
public class Yootil {
	private static final Pattern UNDASHED_UUID_GAPS = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
	private static final String UUID_DASHIFIER_REPLACEMENT = "$1-$2-$3-$4-$5";
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	public static String urlEncode(@Nullable UUID value) {
		return value == null ? "" : value.toString();
	}

	public static String urlEncode(@Nullable String value) {
		if (value == null) return "";

		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}

	public static String hash(byte[]... bytes) {
		// concatenate the arrays
		int space = 0;
		for (byte[] arr : bytes) space += arr.length;
		byte[] theBigMan = new byte[space];

		space = 0;

		for (byte[] arr : bytes) {
			System.arraycopy(arr, 0, theBigMan, space, arr.length);
			space += arr.length;
		}

		try {
			// Minecraft Hash
			// https://gist.github.com/unascribed/70e830d471d6a3272e3f
			return new BigInteger(MessageDigest.getInstance("SHA-1").digest(theBigMan)).toString(16);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("For some reason your computer's java install thinks SHA-1 is not a hashing algorithm.", e);
		}
	}

	public static String loadOrCache(File file, @Nullable String value) {
		try {
			if (value != null) {
				file.createNewFile();

				try (FileWriter writer = new FileWriter(file)) {
					writer.write(value);
				}
			} else if (file.isFile()) {
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					value = reader.readLine().trim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	public static String base64Ip(InetSocketAddress ip) {
		byte[] arr = (ip.getAddress().getHostAddress() + ":" + ip.getPort()).getBytes(StandardCharsets.UTF_8);
		return Base64.encodeBase64String(arr);
	}

	public static String base64(String text) {
		return Base64.encodeBase64String(text.getBytes(StandardCharsets.UTF_8));
	}

	public static String dashifyUUID(String uuid) {
		return UNDASHED_UUID_GAPS.matcher(uuid).replaceAll(UUID_DASHIFIER_REPLACEMENT);
	}

	public static UUID fromUUID(String uuid) {
		return UUID.fromString(uuid.length() == 36 ? uuid : UNDASHED_UUID_GAPS.matcher(uuid).replaceAll(UUID_DASHIFIER_REPLACEMENT));
	}

	public static List<String> toStringList(JsonArray arr) {
		List<String> result = new ArrayList<>();

		for (JsonElement e : arr) {
			result.add(e.getAsString());
		}

		return result;
	}

	public static <T> List<T> map(JsonArray arr, Function<JsonElement, T> mapping) {
		List<T> result = new ArrayList<>();

		for (JsonElement e : arr) {
			result.add(mapping.apply(e));
		}

		return result;
	}

	public static <T> List<T> mapObjects(JsonArray arr, Function<JsonObject, T> mapping) {
		List<T> result = new ArrayList<>();

		for (JsonElement e : arr) {
			result.add(mapping.apply(e.getAsJsonObject()));
		}

		return result;
	}

	public static byte[] randomBytes(int number) {
		byte[] result = new byte[number];
		SECURE_RANDOM.nextBytes(result);
		return result;
	}
}
