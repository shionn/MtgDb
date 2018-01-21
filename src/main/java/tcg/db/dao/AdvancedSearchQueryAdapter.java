package tcg.db.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import tcg.db.dbo.CardTypeClass;
import tcg.search.Filter;
import tcg.search.FilterType;

public class AdvancedSearchQueryAdapter {

	public String search(@Param("types") List<Filter> filters) {
		SQL sql = new SQL().SELECT("c.name").SELECT_DISTINCT("c.card").FROM("card AS c");
		int i=0;
		for (String type : types(filters, FilterType.Type)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='" + CardTypeClass.Type
					+ "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		for (String type : types(filters, FilterType.SubType)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='"
					+ CardTypeClass.SubType + "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		for (String type : types(filters, FilterType.SuperType)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='"
					+ CardTypeClass.SuperType + "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		sql.ORDER_BY("name");
		return sql.toString();
	}

	private List<String> types(List<Filter> filters, FilterType type) {
		return filters.stream().filter(f -> f.getType() == type).map(Filter::getValue).collect(Collectors.toList());
	}

}
