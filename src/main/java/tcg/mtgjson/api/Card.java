package tcg.mtgjson.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;

import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardRarity;
import tcg.mtgjson.api.converter.CardLayoutConverter;
import tcg.mtgjson.api.converter.CardRarityConverter;

public class Card {

	public static class DateConverter extends StdConverter<String, Date> {
		@Override
		public Date convert(String value) {
			if (value.length() == "yyyy".length()) {
				value += "-00-01";
			} else if (value.length() == "yyyy-MM".length()) {
				value += "-01";
			}
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	private String id;
	@JsonDeserialize(converter = CardLayoutConverter.class)
	private CardLayout layout;
	private String name;
	private List<String> names;
	private String manaCost;
	private float cmc;
	private List<String> colors;
	private List<String> colorIdentity;
	private String type;
	private List<String> supertypes;
	private List<String> types;
	private List<String> subtypes;
	@JsonDeserialize(converter = CardRarityConverter.class)
	private CardRarity rarity;
	private String text;
	private String flavor;
	private String artist;
	private String number;
	private String power;
	private String toughness;
	private String loyalty;
	private Integer multiverseid;
	private List<Integer> variations;
	private String imageName;
	private String watermark;
	private String border;
	private boolean timeshifted;
	private int hand;
	private int life;
	private boolean reserved;
	@JsonDeserialize(converter = DateConverter.class)
	private Date releaseDate;
	private boolean starter;
	private String mciNumber;
	private List<Ruling> rulings;
	private List<ForeignName> foreignNames = new ArrayList<>();
	private List<String> printings;
	private String originalText;
	private String originalType;
	private List<Legality> legalities;
	private String source;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CardLayout getLayout() {
		return layout;
	}

	public void setLayout(CardLayout layout) {
		this.layout = layout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public float getCmc() {
		return cmc;
	}

	public void setCmc(float cmc) {
		this.cmc = cmc;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public List<String> getColorIdentity() {
		return colorIdentity;
	}

	public void setColorIdentity(List<String> colorIdentity) {
		this.colorIdentity = colorIdentity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(List<String> supertypes) {
		this.supertypes = supertypes;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(List<String> subtypes) {
		this.subtypes = subtypes;
	}

	public CardRarity getRarity() {
		return rarity;
	}

	public void setRarity(CardRarity rarity) {
		this.rarity = rarity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(String loyalty) {
		this.loyalty = loyalty;
	}

	public Integer getMultiverseid() {
		return multiverseid;
	}

	public void setMultiverseid(Integer multiverseid) {
		this.multiverseid = multiverseid;
	}

	public List<Integer> getVariations() {
		return variations;
	}

	public void setVariations(List<Integer> variations) {
		this.variations = variations;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public boolean isTimeshifted() {
		return timeshifted;
	}

	public void setTimeshifted(boolean timeshifted) {
		this.timeshifted = timeshifted;
	}

	public int getHand() {
		return hand;
	}

	public void setHand(int hand) {
		this.hand = hand;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isStarter() {
		return starter;
	}

	public void setStarter(boolean starter) {
		this.starter = starter;
	}

	public String getMciNumber() {
		return mciNumber;
	}

	public void setMciNumber(String mciNumber) {
		this.mciNumber = mciNumber;
	}

	public List<Ruling> getRulings() {
		return rulings;
	}

	public void setRulings(List<Ruling> rulings) {
		this.rulings = rulings;
	}

	public List<ForeignName> getForeignNames() {
		return foreignNames;
	}

	public void setForeignNames(List<ForeignName> foreignNames) {
		this.foreignNames = foreignNames;
	}

	public List<String> getPrintings() {
		return printings;
	}

	public void setPrintings(List<String> printings) {
		this.printings = printings;
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

	public List<Legality> getLegalities() {
		return legalities;
	}

	public void setLegalities(List<Legality> legalities) {
		this.legalities = legalities;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNameId() {
		return DigestUtils.sha1Hex(name);
	}

	public String getColorsId() {
		String colorId = "";
		if (colors != null) {
			if (colors.contains("White")) {
				colorId += "W";
			}
			if (colors.contains("Blue")) {
				colorId += "U";
			}
			if (colors.contains("Black")) {
				colorId += "B";
			}
			if (colors.contains("Red")) {
				colorId += "R";
			}
			if (colors.contains("Green")) {
				colorId += "G";
			}
		}
		return colorId;
	}

	public String getColorIdentityId() {
		String colorId = "";
		if (colorIdentity != null) {
			if (colorIdentity.contains("W")) {
				colorId += "W";
			}
			if (colorIdentity.contains("U")) {
				colorId += "U";
			}
			if (colorIdentity.contains("B")) {
				colorId += "B";
			}
			if (colorIdentity.contains("R")) {
				colorId += "R";
			}
			if (colorIdentity.contains("G")) {
				colorId += "G";
			}
		}
		return colorId;
	}
}
