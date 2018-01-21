package tcg.db.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import tcg.db.dbo.CardTypeClass;
import tcg.search.Filter;
import tcg.search.FilterType;

public class AdvancedSearchQueryAdapter {

	public String search(@Param("types") List<Filter> filters) {
		SQL sql = new SQL().SELECT("c.id").SELECT("c.name").SELECT("c.card").FROM("card AS c");
		int i=0;
		for (String type : values(filters, FilterType.Type)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='" + CardTypeClass.Type
					+ "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		for (String type : values(filters, FilterType.SubType)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='"
					+ CardTypeClass.SubType + "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		for (String type : values(filters, FilterType.SuperType)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='"
					+ CardTypeClass.SuperType + "' AND t" + i + ".value='" + type + "'");
			i++;
		}
		List<String> keywords = values(filters, FilterType.KeyWord);
		if (!keywords.isEmpty()) {
			sql.WHERE("MATCH(text) AGAINST ('+" + StringUtils.join(keywords, " +") + "' IN BOOLEAN MODE)");
		}
		sql.ORDER_BY("name").GROUP_BY("card");
		return sql.toString();
	}

	private List<String> values(List<Filter> filters, FilterType type) {
		return filters.stream().filter(f -> f.getType() == type).map(Filter::getValue).collect(Collectors.toList());
	}

}
