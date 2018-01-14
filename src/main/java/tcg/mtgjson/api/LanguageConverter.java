package tcg.mtgjson.api;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LanguageConverter extends StdConverter<String, Language> {

	@Override
	public Language convert(String value) {
		switch (value) {
		case "Russian":
			return Language.ru;
		case "Chinese Traditional":
		case "Chinese Simplified":
			return Language.cn;
		case "German":
			return Language.de;
		case "French":
			return Language.fr;
		case "Italian":
			return Language.it;
		case "Japanese":
			return Language.jp;
		case "Portuguese (Brazil)":
			return Language.pt;
		case "Korean":
			return Language.ko;
		case "Spanish":
			return Language.es;
		default:
			throw new IllegalArgumentException(value);
		}
	}
}