package tcg.mtgjson.api;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;

public class Set {
	public enum Border {
		black, silver, white
	}

	public enum Type {
		core, expansion, reprint, box, un, fromthevault, premiumdeck, dueldeck, starter, commander, planechase, archenemy, promo, vanguard, masters, conspiracy, masterpiece;

		public static class Converter extends StdConverter<String, Type> {
			@Override
			public Type convert(String value) {
				return Type.valueOf(value.replaceAll(" ", ""));
			}
		}
	}

	private String name;
	private String code;
	private String gathererCode;
	private String oldCode;
	private String magicCardsInfoCode;
	private Date releaseDate;
	private Border border;

	@JsonDeserialize(converter = Type.Converter.class)
	private Type type;
	private String block;
	private boolean onlineOnly;
	private List<Card> cards;

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

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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
}
