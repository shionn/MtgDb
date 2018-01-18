package tcg.db.dbo;

import java.util.ArrayList;
import java.util.List;

public class Card {

	private String id;
	private String name;
	private String text;
	private String flavor;
	private String manaCost;
	private String type;
	private Edition edition;
	private List<CardPrinting> printings;
	private List<CardPrice> prices = new ArrayList<>();
	private List<CardLang> langs = new ArrayList<>();

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

	public CardPrice getPrice(CardPriceSource source) {
		return prices.stream().filter(card -> card.getSource() == source).findFirst().orElse(null);
	}

	public CardLang lang(Lang lang) {
		return langs.stream().filter(l -> l.getLang() == lang).findFirst().orElse(null);
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

}
