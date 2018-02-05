package tcg.db.dbo;

public class DeckEntry {

	private Card card;
	private int qty;
	private String tag;
	private boolean foil;
	private DeckEntryCategory category;

	public boolean isFoil() {
		return foil;
	}

	public void setFoil(boolean foil) {
		this.foil = foil;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public DeckEntryCategory getCategory() {
		return category;
	}

	public void setCategory(DeckEntryCategory category) {
		this.category = category;
	}

}
