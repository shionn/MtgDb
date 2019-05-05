package tcg.mtgjson.v2.api;

import java.util.Date;
import java.util.List;

public class MtgJsonSet {

	private String name;
	private String code;
	private Date releaseDate;
	private SetType type;
	private String block;
	private boolean isOnlineOnly;
	private List<MtgJsonCard> cards;
	private String mcmName;
	private String mcmId;
	private String keyruneCode;
	private SetTranslation translation;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public SetType getType() {
		return type;
	}

	public void setType(SetType type) {
		this.type = type;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public boolean isOnlineOnly() {
		return isOnlineOnly;
	}

	public void setOnlineOnly(boolean isOnlineOnly) {
		this.isOnlineOnly = isOnlineOnly;
	}

	public SetTranslation getTranslation() {
		return translation;
	}

	public void setTranslation(SetTranslation translation) {
		this.translation = translation;
	}

	public String getMcmName() {
		return mcmName;
	}

	public void setMcmName(String mcmName) {
		this.mcmName = mcmName;
	}

	public String getMcmId() {
		return mcmId;
	}

	public void setMcmId(String mcmId) {
		this.mcmId = mcmId;
	}

	public String getKeyruneCode() {
		return keyruneCode;
	}

	public void setKeyruneCode(String keyruneCode) {
		this.keyruneCode = keyruneCode;
	}

	public List<MtgJsonCard> getCards() {
		return cards;
	}

	public void setCards(List<MtgJsonCard> cards) {
		this.cards = cards;
	}
}
