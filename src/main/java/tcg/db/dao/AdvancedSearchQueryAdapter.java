package tcg.db.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.GameLegality;
import tcg.search.Filter;
import tcg.search.FilterType;

public class AdvancedSearchQueryAdapter {

	public String search(@Param("types") List<Filter> filters) {
		SQL sql = new SQL().SELECT("c.id, c.name, c.card, c.mana_cost, c.type").FROM("card AS c");
		int i = 0;
		for (String type : values(filters, FilterType.Type)) {
			sql.INNER_JOIN("card_type AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".type='"
					+ CardTypeClass.Type + "' AND t" + i + ".value='" + type + "'");
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
		for (String format : values(filters, FilterType.Format)) {
			sql.INNER_JOIN(
					"card_legality AS t" + i + " ON c.id = t" + i + ".id AND t" + i + ".format='"
							+ format + "' AND t" + i + ".legality='" + GameLegality.Legal + "'");
			i++;
		}
		List<String> editions = values(filters, FilterType.Edition);
		if (editions.size() == 1) {
			sql.INNER_JOIN("(SELECT edition, id FROM card) AS e ON c.id = e.id "
					+ "AND e.edition='" + editions.get(0) + "'");
		} else {
			for (String code : editions) {
				sql.INNER_JOIN("(SELECT edition, card FROM card) AS e" + i + " ON c.card = e" + i
						+ ".card AND e" + i + ".edition='" + code + "'");
				i++;
			}
		}
		List<String> keywords = values(filters, FilterType.KeyWord);
		if (!keywords.isEmpty()) {
			keywords = keywords.stream().map(s -> '+' + (s.indexOf(' ') == -1 ? s : '"' + s + '"'))
					.collect(Collectors.toList());
			sql.WHERE("MATCH(c.text) AGAINST ('" + StringUtils.join(keywords, ' ')
					+ "' IN BOOLEAN MODE)");
		}
		List<String> cmcs = values(filters, FilterType.ConvertedManaCost);
		if (!cmcs.isEmpty()) {
			sql.WHERE("c.cmc IN (" + StringUtils.join(cmcs, ",") + ")");
		}
		List<String> rarities = values(filters, FilterType.Rarity);
		if (!rarities.isEmpty()) {
			List<String> conditions = rarities.stream().map(s -> "c.rarity='" + s + "'")
					.collect(Collectors.toList());
			sql.WHERE('(' + StringUtils.join(conditions, " OR ") + ')');
		}
		List<String> pats = values(filters, FilterType.PowerAndToughness);
		if (!pats.isEmpty()) {
			List<String> conditions = pats.stream().map(s -> StringUtils.split(s, '/'))
					.map(s -> "c.power='" + s[0] + "' AND c.toughness='" + s[1] + "'")
					.collect(Collectors.toList());
			sql.WHERE('(' + StringUtils.join(conditions, " OR ") + ')');
		}
		List<String> colors = values(filters, FilterType.Color);
		if (!colors.isEmpty()) {
			String color = "";
			if (colors.contains("White")) {
				color += "W";
			}
			if (colors.contains("Blue")) {
				color += "U";
			}
			if (colors.contains("Black")) {
				color += "B";
			}
			if (colors.contains("Red")) {
				color += "R";
			}
			if (colors.contains("Green")) {
				color += "G";
			}
			sql.WHERE("c.colors='" + color + "'");
		}

		for (String name : values(filters, FilterType.Name)) {
			sql.WHERE("c.name LIKE '%" + name + "%'");
		}
		for (String text : values(filters, FilterType.Text)) {
			sql.WHERE("c.text LIKE '%" + text + "%'");
		}
		sql.ORDER_BY("name").GROUP_BY("card");
		return sql.toString() + " LIMIT 1000";
	}

	private List<String> values(List<Filter> filters, FilterType type) {
		return filters.stream().filter(f -> f.getType() == type).map(Filter::getValue)
				.collect(Collectors.toList());
	}

}
