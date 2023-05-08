package cc.cosmetica.impl.cosmetic;

import cc.cosmetica.api.cosmetic.Cosmetic;
import cc.cosmetica.api.cosmetic.CosmeticType;
import cc.cosmetica.api.cosmetic.UploadState;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * An abstract cosmetic that declares some basic shared functionality.
 */
public abstract class AbstractCosmetic implements Cosmetic {
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o instanceof Cosmetic) {
			Cosmetic otherCosmetic = (Cosmetic) o;
			return this.getId().equals(otherCosmetic.getId()) && this.getType().equals(otherCosmetic.getType());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getType(), this.getId());
	}

	/**
	 * Parse a {@link JsonObject} from the API representing a cosmetic into a an actual {@link Cosmetic} object.
	 * @param json the json object representation.
	 * @return an optional containing the parsed cosmetic object, or empty.
	 */
	public static Optional<? extends Cosmetic> parse(@Nullable JsonObject json) {
		if (json == null) {
			return Optional.empty();
		}

		CosmeticType<?> type = CosmeticType.fromTypeString(json.get("type").getAsString()).get();

		if ("".equals(json.get("extraInfo").getAsString())) {
			return SimpleCosmetic.parseAsSimple(json);
		}
		else if (type == CosmeticType.CAPE) {
			return CapeImpl.parseAsCape(json);
		}
		else {
			return ModelImpl.parseAsModel(json);
		}
	}

	public static SimpleCosmetic createDummy() {
		return new SimpleCosmetic(
				CosmeticType.CAPE,
				"Dummy Cape",
				"DUMMY",
				"Cosmetica",
				UUID.randomUUID(),
				UploadState.APPROVED,
				"",
				0
		);
	}
}
