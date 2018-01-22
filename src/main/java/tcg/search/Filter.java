package tcg.search;

import org.apache.commons.lang3.text.WordUtils;

import tcg.db.dbo.CardRarity;
import tcg.db.dbo.Edition;

public class Filter {

	private String value;
	private FilterType type;
	private String display;

	public Filter(FilterType type, String value) {
		this.type = type;
		this.value = value;
	}

	public Filter(Edition e) {
		this.type = FilterType.Edition;
		this.value = e.getCode();
		this.display = e.getName();
	}

	public Filter(CardRarity rarity) {
		this.type = FilterType.Rarity;
		this.value = rarity.name();
		this.display = WordUtils.capitalize(rarity.name());
	}

	public FilterType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getDisplay() {
		return display == null ? value : display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

}
