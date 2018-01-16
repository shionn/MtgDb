package tcg.db.dbo;

import java.util.List;

public class Card {

	private String id;
	private String name;
	private String text;
	private String manaCost;
	private String type;
	private Edition edition;
	private List<CardPrinting> printings;

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

	public boolean isCreature() {
		return type != null && type.toLowerCase().indexOf("creature") != -1;
	}

	public boolean isEnchantment() {
		return type != null && type.toLowerCase().indexOf("enchantment") != -1;
	}
}
