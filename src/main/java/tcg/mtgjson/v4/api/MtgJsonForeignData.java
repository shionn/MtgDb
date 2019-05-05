package tcg.mtgjson.v4.api;

/**
 * @see https://mtgjson.com/structures/foreign-data/
 */
public class MtgJsonForeignData {
	private String flavorText;
	private String language;
	private String name;
	private String text;

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
