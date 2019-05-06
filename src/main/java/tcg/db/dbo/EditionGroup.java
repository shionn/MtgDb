package tcg.db.dbo;

import java.util.List;

public class EditionGroup {
	private String name;
	private List<Edition> editions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Edition> getEditions() {
		return editions;
	}

	public void setEditions(List<Edition> editions) {
		this.editions = editions;
	}

}
