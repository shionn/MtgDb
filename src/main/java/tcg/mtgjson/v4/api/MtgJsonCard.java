package tcg.mtgjson.v4.api;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardRarity;
import tcg.mtgjson.CardLayoutConverter;
import tcg.mtgjson.v3.api.converter.CardRarityConverter;

/**
 * @See https://mtgjson.com/structures/card/
 */
public class MtgJsonCard {
	private String artist;
	private List<String> colorIdentity;
	private List<String> colors;
	private float convertedManaCost;
	private String flavorText;
	// TODO private List<ForeignData> foreignData;
	private boolean hasFoil, hasNonFoil, isAlternative, isOnlineOnly, isOversized, isReserved;
	@JsonDeserialize(converter = CardLayoutConverter.class)
	private CardLayout layout;
	// TODO List<Legality> legalities;
	private String loyalty;
	private String manaCost;
	private String name;
	private String number;
	private String originalText;
	private String originalType;
	private String power;
	@JsonDeserialize(converter = CardRarityConverter.class)
	private CardRarity rarity;
	// TODO private List<Ruling> rulings;
	private List<String> subtypes;
	private List<String> supertypes;
	private String text;
	private String toughness;
	private String type;
	private List<String> types;
	private String uuid;

	public String getColorsId() {
		return StringUtils.join(colors.toArray());
	}

	public String getColorIdentityId() {
		return StringUtils.join(colorIdentity.toArray());
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

}
