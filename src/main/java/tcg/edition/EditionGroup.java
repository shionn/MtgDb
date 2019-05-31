package tcg.edition;

import java.util.List;

import tcg.db.dbo.Edition;

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
