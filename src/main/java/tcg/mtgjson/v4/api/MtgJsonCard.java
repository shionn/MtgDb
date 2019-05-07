package tcg.mtgjson.v4.api;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardRarity;
import tcg.mtgjson.v4.api.converter.CardLayoutConverter;
import tcg.mtgjson.v4.api.converter.CardRarityConverter;

/**
 * @See https://mtgjson.com/structures/card/
 */
public class MtgJsonCard {
	private String artist;
	private List<String> colorIdentity;
	private List<String> colors;
	private float convertedManaCost;
	private String flavorText;
	private List<MtgJsonForeignData> foreignData;
	private boolean hasFoil, hasNonFoil, isAlternative, isOnlineOnly, isOversized, isReserved;
	@JsonDeserialize(converter = CardLayoutConverter.class)
	private CardLayout layout;
	private MtgJsonLegalities legalities;
	private String loyalty;
	private String manaCost;
	private String name;
	private List<String> names;
	private String number;
	private String originalText;
	private String originalType;
	private String power;
	@JsonDeserialize(converter = CardRarityConverter.class)
	private CardRarity rarity;
	private List<MtgJsonRuling> rulings;
	private String scryfallId;
	private String scryfallIllustrationId;
	private String side;
	private List<String> subtypes;
	private List<String> supertypes;
	private String text;
	private String toughness;
	private String type;
	private List<String> types;
	private String uuid;

	public String getColorsId() {
		String id = "";
		if (colors.contains("W")) {
			id += "W";
		}
		if (colors.contains("U")) {
			id += "U";
		}
		if (colors.contains("B")) {
			id += "B";
		}
		if (colors.contains("R")) {
			id += "R";
		}
		if (colors.contains("G")) {
			id += "G";
		}
		return id;
	}

	public String getColorIdentityId() {
		String id = "";
		if (colorIdentity.contains("W")) {
			id += "W";
		}
		if (colorIdentity.contains("U")) {
			id += "U";
		}
		if (colorIdentity.contains("B")) {
			id += "B";
		}
		if (colorIdentity.contains("R")) {
			id += "R";
		}
		if (colorIdentity.contains("G")) {
			id += "G";
		}
		return id;
	}

	public Foil getFoil() {
		if (hasFoil && hasNonFoil) {
			return Foil.both;
		}
		if (hasFoil) {
			return Foil.onlyfoil;
		}
		return Foil.nofoil;
	}

	public String getNameId() {
		return DigestUtils.sha1Hex(name);
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public List<String> getColorIdentity() {
		return colorIdentity;
	}

	public void setColorIdentity(List<String> colorIdentity) {
		this.colorIdentity = colorIdentity;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public float getConvertedManaCost() {
		return convertedManaCost;
	}

	public void setConvertedManaCost(float convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public boolean isHasFoil() {
		return hasFoil;
	}

	public void setHasFoil(boolean hasFoil) {
		this.hasFoil = hasFoil;
	}

	public boolean isHasNonFoil() {
		return hasNonFoil;
	}

	public void setHasNonFoil(boolean hasNonFoil) {
		this.hasNonFoil = hasNonFoil;
	}

	public boolean isAlternative() {
		return isAlternative;
	}

	public void setAlternative(boolean isAlternative) {
		this.isAlternative = isAlternative;
	}

	public boolean isOnlineOnly() {
		return isOnlineOnly;
	}

	public void setOnlineOnly(boolean isOnlineOnly) {
		this.isOnlineOnly = isOnlineOnly;
	}

	public boolean isOversized() {
		return isOversized;
	}

	public void setOversized(boolean isOversized) {
		this.isOversized = isOversized;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public CardLayout getLayout() {
		return layout;
	}

	public void setLayout(CardLayout layout) {
		this.layout = layout;
	}

	public String getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(String loyalty) {
		this.loyalty = loyalty;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalType() {
		return originalType;
	}

	public void setOriginalType(String originalType) {
		this.originalType = originalType;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public CardRarity getRarity() {
		return rarity;
	}

	public void setRarity(CardRarity rarity) {
		this.rarity = rarity;
	}

	public List<String> getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(List<String> subtypes) {
		this.subtypes = subtypes;
	}

	public List<String> getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(List<String> supertypes) {
		this.supertypes = supertypes;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<MtgJsonRuling> getRulings() {
		return rulings;
	}

	public void setRulings(List<MtgJsonRuling> rulings) {
		this.rulings = rulings;
	}

	public MtgJsonLegalities getLegalities() {
		return legalities;
	}

	public void setLegalities(MtgJsonLegalities legalities) {
		this.legalities = legalities;
	}

	public List<MtgJsonForeignData> getForeignData() {
		return foreignData;
	}

	public void setForeignData(List<MtgJsonForeignData> foreignData) {
		this.foreignData = foreignData;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getScryfallIllustrationId() {
		return scryfallIllustrationId;
	}

	public void setScryfallIllustrationId(String scryfallIllustrationId) {
		this.scryfallIllustrationId = scryfallIllustrationId;
	}

	public String getScryfallId() {
		return scryfallId;
	}

	public void setScryfallId(String scryfallId) {
		this.scryfallId = scryfallId;
	}

}
