package tcg.mtgjson.api.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import tcg.db.dbo.CardLayout;

public class CardLayoutConverter extends StdConverter<String, CardLayout> {

	@Override
	public CardLayout convert(String value) {
		return CardLayout.valueOf(value.replaceAll("-", ""));
	}

}
