package tcg.search;

public class Filter {

	private String value;
	private FilterType type;

	public Filter(FilterType type, String value) {
		this.type = type;
		this.value = value;
	}

	public FilterType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

}
