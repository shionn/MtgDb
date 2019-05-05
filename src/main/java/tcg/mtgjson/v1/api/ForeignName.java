package tcg.mtgjson.v1.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import tcg.mtgjson.v1.api.converter.LanguageConverter;

public class ForeignName {

	@JsonDeserialize(converter = LanguageConverter.class)
	private Language language;
	private String name;
	private int multiverseid;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMultiverseid() {
		return multiverseid;
	}

	public void setMultiverseid(int multiverseid) {
		this.multiverseid = multiverseid;
	}
}
