package tcg.mtgjson.v3.api.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import tcg.db.dbo.CardRarity;

public class CardRarityConverter extends StdConverter<String, CardRarity> {

	@Override
	public CardRarity convert(String value) {
		return CardRarity.valueOf(value.toLowerCase().replaceAll(" ", ""));
	}

}
