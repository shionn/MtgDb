package tcg.db.dbo;

import java.util.List;

public class Card {

	private String id;
	private String name;
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
}
