package tcg.db.dbo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class Card {
	public enum Foil {
		both, onlyfoil, nofoil
	}

	private String id;
	private String card;
	private String linkCardId;
	private String scryfallId;
	private Card linkCard;
	private String name;
	private String text;
	private String originalText;
	private String artist;
	private String flavor;
	private String manaCost;
	private String colors;
	private String type;
	private String originalType;
	private String power;
	private String toughness;
	private String number;
	private String side;

	private int loyalty;
	private int cmc;

	private CardLayout layout;
	private CardRarity rarity;
	private Edition edition;
	private Foil foil;
	private List<CardPrinting> printings;
	private List<CardPrice> prices = new ArrayList<>();
	private List<CardLang> langs = new ArrayList<>();
	private List<CardRule> rules = new ArrayList<>();
	private List<Legality> legalities = new ArrayList<>();

	public boolean isCreature() {
		return type != null && type.toLowerCase().indexOf("creature") != -1;
	}

	public boolean isEnchantment() {
		return type != null && type.toLowerCase().indexOf("enchantment") != -1;
	}

	public boolean isPlaneswalker() {
		return type != null && type.toLowerCase().indexOf("planeswalker") != -1;
	}

	public boolean isInstant() {
		return type != null && type.toLowerCase().indexOf("instant") != -1;
	}

	public boolean isSorcery() {
		return type != null && type.toLowerCase().indexOf("sorcery") != -1;
	}

	public boolean isLand() {
		return type != null && type.toLowerCase().indexOf("land") != -1;
	}

	public boolean isArtifact() {
		return type != null && type.toLowerCase().indexOf("artifact") != -1;
	}

	public boolean isSnow() {
		return type != null && type.toLowerCase().indexOf("snow") != -1;
	}

	public boolean isDisplayOriginal() {
		return !(Objects.equals(text, originalText) && Objects.equals(type, originalType))
				&& StringUtils.isNotBlank(originalText) && StringUtils.isNotBlank(originalType);
	}

	public CardPrice getPrice(CardPriceSource source) {
		return prices.stream().filter(card -> card.getSource() == source).findFirst().orElse(null);
	}

	public Date getLastPriceDate() {
		return prices.stream().filter(Objects::nonNull) //
				.map(CardPrice::getPriceDate) //
				.filter(Objects::nonNull) //
				.max(Date::compareTo) //
				.orElse(null);
	}

	public boolean isOldPrice() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);
		Date last = getLastPriceDate();
		return last == null || yesterday.getTime().after(last);
	}

	public CardLang lang(Lang lang) {
		return langs.stream().filter(l -> l.getLang() == lang).findFirst().orElse(null);
	}

	public boolean isFlip() {
		return layout == CardLayout.doublefaced || layout == CardLayout.meld;
	}

	public boolean haveLinkCard() {
		return CardLayout.links().contains(layout);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CardPrinting> getPrintings() {
		return printings;
	}

	public void setPrintings(List<CardPrinting> printings) {
		this.printings = printings;
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
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

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CardPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<CardPrice> prices) {
		this.prices = prices;
	}

	public List<CardLang> getLangs() {
		return langs;
	}

	public void setLangs(List<CardLang> langs) {
		this.langs = langs;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalType(String originalType) {
		this.originalType = originalType;
	}

	public String getOriginalType() {
		return originalType;
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

	public int getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(int loyalty) {
		this.loyalty = loyalty;
	}

	public List<CardRule> getRules() {
		return rules;
	}

	public void setRules(List<CardRule> rules) {
		this.rules = rules;
	}

	public String getLinkCardId() {
		return linkCardId;
	}

	public void setLinkCardId(String linkCardId) {
		this.linkCardId = linkCardId;
	}

	public Card getLinkCard() {
		return linkCard;
	}

	public void setLinkCard(Card linkCard) {
		this.linkCard = linkCard;
	}

	public CardLayout getLayout() {
		return layout;
	}

	public void setLayout(CardLayout layout) {
		this.layout = layout;
	}

	public CardRarity getRarity() {
		return rarity;
	}

	public void setRarity(CardRarity rarity) {
		this.rarity = rarity;
	}

	public int getCmc() {
		return cmc;
	}

	public void setCmc(int cmc) {
		this.cmc = cmc;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public GuildColor getGuildColor() {
		return GuildColor.from(colors);
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public List<Legality> getLegalities() {
		return legalities;
	}

	public void setLegalities(List<Legality> legalities) {
		this.legalities = legalities;
	}

	public Foil getFoil() {
		return foil;
	}

	public void setFoil(Foil foil) {
		this.foil = foil;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getScryfallId() {
		return scryfallId;
	}

	public void setScryfallId(String scryfallId) {
		this.scryfallId = scryfallId;
	}
}
