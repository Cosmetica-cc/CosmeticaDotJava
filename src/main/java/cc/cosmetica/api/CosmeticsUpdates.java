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
 * An update retrieved on the safari. Contains a list of notifications for this user, a list of users on the same server that need their cosmetics refreshed, and a timestamp to use next time you make a request to the {@code everythirtysecondsinafricahalfaminutepasses} endpoint.
 */
public final class CosmeticsUpdates {
	public CosmeticsUpdates(List<String> notifications, List<User> needsUpdating, long timestamp) {
		this.notifications = notifications;
		this.needsUpdating = needsUpdating;
		this.timestamp = timestamp;
	}

	private final List<String> notifications;
	private final List<User> needsUpdating;
	private final long timestamp;

	public List<String> getNotifications() {
		return notifications;
	}

	public List<User> getNeedsUpdating() {
		return needsUpdating;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		CosmeticsUpdates that = (CosmeticsUpdates) obj;
		return Objects.equals(this.notifications, that.notifications) &&
				Objects.equals(this.needsUpdating, that.needsUpdating) &&
				this.timestamp == that.timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(notifications, needsUpdating, timestamp);
	}

	@Override
	public String toString() {
		return "CosmeticsUpdates[" +
				"notifications=" + notifications + ", " +
				"needsUpdating=" + needsUpdating + ", " +
				"timestamp=" + timestamp + ']';
	}

}
