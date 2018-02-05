package tcg.db.dbo;

import java.util.List;
import java.util.stream.Collectors;

public class Deck {
	private int id;
	private String name;
	private DeckType type;
	private List<DeckEntry> cards;

	// color, value
	// event statistique ?

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeckType getType() {
		return type;
	}

	public void setType(DeckType type) {
		this.type = type;
	}

	public List<DeckEntry> getCards() {
		return cards;
	}

	public void setCards(List<DeckEntry> cards) {
		this.cards = cards;
	}

	public List<DeckEntry> getMains() {
		return this.cards.stream().filter(c -> c.getCategory() == DeckEntryCategory.main)
				.collect(Collectors.toList());
	}

}
