package tcg.db.dbo;

public class Deck {
	private int id;
	private String name;
	private DeckType type;

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
}
