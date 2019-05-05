package tcg.mtgjson.v1.api;

import java.util.Date;
import java.util.List;

public class Set {
	private String name;
	private String code;
	private String gathererCode;
	private String oldCode;
	private String magicCardsInfoCode;
	private Date releaseDate;
	private String border;
	private String type;
	private String block;
	private boolean onlineOnly;
	private List<Card> cards;
	private String mkm_name;
	private String mkm_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGathererCode() {
		return gathererCode;
	}

	public void setGathererCode(String gathererCode) {
		this.gathererCode = gathererCode;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getMagicCardsInfoCode() {
		return magicCardsInfoCode;
	}

	public void setMagicCardsInfoCode(String magicCardsInfoCode) {
		this.magicCardsInfoCode = magicCardsInfoCode;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public boolean isOnlineOnly() {
		return onlineOnly;
	}

	public void setOnlineOnly(boolean onlineOnly) {
		this.onlineOnly = onlineOnly;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getMkm_id() {
		return mkm_id;
	}

	public void setMkm_id(String mkm_id) {
		this.mkm_id = mkm_id;
	}

	public String getMkm_name() {
		return mkm_name;
	}

	public void setMkm_name(String mkm_name) {
		this.mkm_name = mkm_name;
	}
}
