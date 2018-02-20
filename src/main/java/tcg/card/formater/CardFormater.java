package tcg.card.formater;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;

@Component
public class CardFormater {

	private Map<Pattern, String> patterns = buildTextPatterns();
	private Map<Pattern, String> manacosts = buildManacostsPatterns();


	private Map<Pattern, String> buildTextPatterns() {
		Map<Pattern, String> patterns = new HashMap<>();
		patterns.put(Pattern.compile("\n"), "<br/>");
		patterns.put(Pattern.compile("\\("), "<em>(");
		patterns.put(Pattern.compile("\\)"), ")</em>");
		for (int i = 0; i <= 20; i++) {
			patterns.put(Pattern.compile("\\{" + i + "\\}"),
					"<i class=\"mi mi-" + i + " mi-mana\"></i>");
		}
		patterns.put(Pattern.compile("\\{W\\}"), "<i class=\"mi mi-w mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{U\\}"), "<i class=\"mi mi-u mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{B\\}"), "<i class=\"mi mi-b mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{R\\}"), "<i class=\"mi mi-r mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{G\\}"), "<i class=\"mi mi-g mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{X\\}"), "<i class=\"mi mi-x mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{C\\}"), "<i class=\"mi mi-c mi-mana\"></i>");

		patterns.put(Pattern.compile("\\{2/W\\}"), split("2", "w"));
		patterns.put(Pattern.compile("\\{2/U\\}"), split("2", "u"));
		patterns.put(Pattern.compile("\\{2/B\\}"), split("2", "b"));
		patterns.put(Pattern.compile("\\{2/R\\}"), split("2", "r"));
		patterns.put(Pattern.compile("\\{2/G\\}"), split("2", "g"));

		patterns.put(Pattern.compile("\\{W/U\\}"), split("w", "u"));
		patterns.put(Pattern.compile("\\{U/B\\}"), split("u", "b"));
		patterns.put(Pattern.compile("\\{B/R\\}"), split("b", "r"));
		patterns.put(Pattern.compile("\\{R/G\\}"), split("r", "g"));
		patterns.put(Pattern.compile("\\{G/W\\}"), split("g", "w"));

		patterns.put(Pattern.compile("\\{W/B\\}"), split("w", "b"));
		patterns.put(Pattern.compile("\\{U/R\\}"), split("u", "r"));
		patterns.put(Pattern.compile("\\{B/G\\}"), split("b", "g"));
		patterns.put(Pattern.compile("\\{R/W\\}"), split("r", "w"));
		patterns.put(Pattern.compile("\\{G/U\\}"), split("g", "u"));

		patterns.put(Pattern.compile("\\{W/P\\}"), phyrexia("w"));
		patterns.put(Pattern.compile("\\{U/P\\}"), phyrexia("u"));
		patterns.put(Pattern.compile("\\{B/P\\}"), phyrexia("b"));
		patterns.put(Pattern.compile("\\{R/P\\}"), phyrexia("r"));
		patterns.put(Pattern.compile("\\{G/P\\}"), phyrexia("g"));

		patterns.put(Pattern.compile("\\{T\\}"), "<i class=\"mi mi-t\"></i>");
		return patterns;
	}


	private String split(String m1, String m2) {
		return "<span class=\"mi-split\"><i class=\"mi mi-" + m1 + "\"></i><i class=\"mi mi-" + m2 + "\"></i></span>";
	}

	private String phyrexia(String mana) {
		return "<i class=\"mi mi-p mi-mana-" + mana + "\"></i>";
	}

	private Map<Pattern, String> buildManacostsPatterns() {
		Map<Pattern, String> patterns = new HashMap<>();
		patterns.put(Pattern.compile("\\{W\\}"), "<i class=\"mi mi-w mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{U\\}"), "<i class=\"mi mi-u mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{B\\}"), "<i class=\"mi mi-b mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{R\\}"), "<i class=\"mi mi-r mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{G\\}"), "<i class=\"mi mi-g mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{X\\}"), "<i class=\"mi mi-x mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{C\\}"), "<i class=\"mi mi-c mi-mana mi-shadow\"></i>");

		patterns.put(Pattern.compile("\\{2/W\\}"), splitShadow("2", "w"));
		patterns.put(Pattern.compile("\\{2/U\\}"), splitShadow("2", "u"));
		patterns.put(Pattern.compile("\\{2/B\\}"), splitShadow("2", "b"));
		patterns.put(Pattern.compile("\\{2/R\\}"), splitShadow("2", "r"));
		patterns.put(Pattern.compile("\\{2/G\\}"), splitShadow("2", "g"));

		patterns.put(Pattern.compile("\\{W/U\\}"), splitShadow("w", "u"));
		patterns.put(Pattern.compile("\\{U/B\\}"), splitShadow("u", "b"));
		patterns.put(Pattern.compile("\\{B/R\\}"), splitShadow("b", "r"));
		patterns.put(Pattern.compile("\\{R/G\\}"), splitShadow("r", "g"));
		patterns.put(Pattern.compile("\\{G/W\\}"), splitShadow("g", "w"));

		patterns.put(Pattern.compile("\\{W/B\\}"), splitShadow("w", "b"));
		patterns.put(Pattern.compile("\\{U/R\\}"), splitShadow("u", "r"));
		patterns.put(Pattern.compile("\\{B/G\\}"), splitShadow("b", "g"));
		patterns.put(Pattern.compile("\\{R/W\\}"), splitShadow("r", "w"));
		patterns.put(Pattern.compile("\\{G/U\\}"), splitShadow("g", "u"));

		patterns.put(Pattern.compile("\\{W/P\\}"), phyrexiaShadow("w"));
		patterns.put(Pattern.compile("\\{U/P\\}"), phyrexiaShadow("u"));
		patterns.put(Pattern.compile("\\{B/P\\}"), phyrexiaShadow("b"));
		patterns.put(Pattern.compile("\\{R/P\\}"), phyrexiaShadow("r"));
		patterns.put(Pattern.compile("\\{G/P\\}"), phyrexiaShadow("g"));

		for (int i = 0; i <= 20; i++) {
			patterns.put(Pattern.compile("\\{" + i + "\\}"),
					"<i class=\"mi mi-" + i + " mi-mana mi-shadow\"></i>");
		}
		return patterns;
	}

	private String splitShadow(String m1, String m2) {
		return "<span class=\"mi-split mi-shadow\"><i class=\"mi mi-" + m1 + "\"></i><i class=\"mi mi-" + m2
				+ "\"></i></span>";
	}

	private String phyrexiaShadow(String mana) {
		return "<i class=\"mi mi-p mi-mana-" + mana + " mi-shadow\"></i>";
	}

	public String text(String text) {
		if (text != null) {
			for (Entry<Pattern, String> e : patterns.entrySet()) {
				text = e.getKey().matcher(text).replaceAll(e.getValue());
			}
		}
		return text;
	}

	public String manaCost(Card card) {
		String manaCost = card.getManaCost();
		if (manaCost != null) {
			for (Entry<Pattern, String> e : manacosts.entrySet()) {
				manaCost = e.getKey().matcher(manaCost).replaceAll(e.getValue());
			}
		}
		return manaCost;
	}

	public String flavor(Card card) {
		String flavor = card.getFlavor();
		if (flavor != null) {
			flavor = Pattern.compile("\n").matcher(flavor).replaceAll("<br/>");
		}
		return flavor;
	}

}
