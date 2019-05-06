package tcg.mtgjson.v4.api.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import tcg.db.dbo.EditionType;

public class EditionTypeConverter extends StdConverter<String, EditionType> {

	@Override
	public EditionType convert(String value) {
		return EditionType.valueOf(value.toLowerCase().replaceAll(" |_", ""));
	}

}
