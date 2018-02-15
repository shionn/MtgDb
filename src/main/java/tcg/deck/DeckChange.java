package tcg.deck;

import tcg.db.dbo.DeckEntry;

public class DeckChange {

	private int qty;
	private DeckEntry source;
	private DeckEntry dest;

	public DeckChange(int qty) {
		this.qty = qty;
	}

	public DeckChange(DeckEntry source, DeckEntry dest) {
		this.source = source;
		this.dest = dest;
	}

	public int getQty() {
		return qty;
	}

	public DeckEntry getSource() {
		return source;
	}

	public DeckEntry getDest() {
		return dest;
	}
}
