package tcg.db.dbo;

import java.util.List;
import java.util.stream.Collectors;

public class Deck {
	private int id;
	private String name;
	private DeckType type;
	private List<DeckEntry> cards;

	// value
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
				.sorted(this::sort).collect(Collectors.toList());
	}

	public List<DeckEntry> cubeColor(GuildColor color) {
		return this.cards.stream().filter(c -> c.getCategory() == DeckEntryCategory.main)
				.filter(c -> c.getCard().getGuildColor() == color) //
				.filter(c -> !c.getCard().isLand()) //
				.sorted(this::sort) //
				.collect(Collectors.toList());
	}

	public List<DeckEntry> cubeLand() {
		return this.cards.stream().filter(c -> c.getCategory() == DeckEntryCategory.main)
				.filter(c -> c.getCard().isLand()) //
				.sorted(this::sort) //
				.collect(Collectors.toList());
	}

	public List<DeckEntry> getSides() {
		return this.cards.stream().filter(c -> c.getCategory() == DeckEntryCategory.side)
				.sorted(this::sort) //
				.collect(Collectors.toList());
	}

	public int count(DeckEntryCategory category) {
		return cards.stream().filter(e -> e.getCategory() == category)
				.collect(Collectors.summingInt(DeckEntry::getQty));
	}

	private int sort(DeckEntry e, DeckEntry f) {
		int result = -Boolean.compare(e.getCard().isCreature(), f.getCard().isCreature());
		if (result == 0) {
			result = Boolean.compare(e.getCard().isLand(), f.getCard().isLand());
		}
		if (result == 0) {
			result = Integer.compare(e.getCard().getGuildColor().ordinal(),
					f.getCard().getGuildColor().ordinal());
		}
		if (result == 0) {
			result = Integer.compare(e.getCard().getCmc(), f.getCard().getCmc());
		}
		if (result == 0) {
			result = e.getCard().getName().compareTo(f.getCard().getName());
		}
		return result;
	}

}
