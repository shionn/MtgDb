package tcg.db.dbo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
	private int id;
	private String name;
	private String user;
	private DeckView view;
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

	public List<DeckEntry> getFlats() {
		return this.cards.stream().sorted(this::sort).collect(Collectors.toList());
	}

	public List<DeckEntry> getCommanders() {
		return this.cards.stream().filter(c -> c.getCategory() == DeckEntryCategory.commander)
				.sorted(this::sort).collect(Collectors.toList());
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
		int result =  Integer.compare(e.getCategory().ordinal(),f.getCategory().ordinal());
		if (result == 0) {
			result = -Boolean.compare(e.getCard().isCreature(), f.getCard().isCreature());
		}
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

	public DeckView getView() {
		return view;
	}

	public void setView(DeckView view) {
		this.view = view;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public CardPrice getMkmPrice() {
		return cards.stream() //
				.filter(e -> e.getMkmPrice() != null && e.getMkmPrice().getPrice() != null) //
				.map(e -> e.getMkmPrice().mul(BigDecimal.valueOf(e.getQty()))) //
				.reduce(CardPrice::add) //
				.orElse(new CardPrice());
	}

	public CardPrice getMtgGoldFishPrice() {
		return cards.stream() //
				.filter(e -> e.getMtgGoldFishPrice() != null
						&& e.getMtgGoldFishPrice().getPrice() != null) //
				.map(e -> e.getMtgGoldFishPrice().mul(BigDecimal.valueOf(e.getQty()))) //
				.reduce(CardPrice::add) //
				.orElse(new CardPrice());
	}

	public CardPrice getMtgGoldFishTxPrice() {
		return cards.stream() //
				.filter(e -> e.getMtgGoldFishTxPrice() != null
						&& e.getMtgGoldFishTxPrice().getPrice() != null) //
				.map(e -> e.getMtgGoldFishTxPrice().mul(BigDecimal.valueOf(e.getQty()))) //
				.reduce(CardPrice::add) //
				.orElse(new CardPrice());
	}

}
