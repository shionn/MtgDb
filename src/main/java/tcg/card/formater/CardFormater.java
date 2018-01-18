package tcg.card.formater;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;

@Component
public class CardFormater {

	private Map<Pattern, String> patterns = buildPatterns();
	private Map<Pattern, String> manacosts = buildManacostsPatterns();


	private Map<Pattern, String> buildPatterns() {
		Map<Pattern, String> patterns = new HashMap<>();
		patterns.put(Pattern.compile("\n"), "<br/>");
		patterns.put(Pattern.compile("\\("), "<em>(");
		patterns.put(Pattern.compile("\\)"), ")</em>");
		for (int i = 0; i < 10; i++) {
			patterns.put(Pattern.compile("\\{" + i + "\\}"),
					"<i class=\"mi mi-" + i + " mi-mana\"></i>");
		}
		patterns.put(Pattern.compile("\\{W\\}"), "<i class=\"mi mi-w mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{U\\}"), "<i class=\"mi mi-u mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{B\\}"), "<i class=\"mi mi-b mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{R\\}"), "<i class=\"mi mi-r mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{G\\}"), "<i class=\"mi mi-g mi-mana\"></i>");
		patterns.put(Pattern.compile("\\{T\\}"), "<i class=\"mi mi-t\"></i>");
		return patterns;
	}

	private Map<Pattern, String> buildManacostsPatterns() {
		Map<Pattern, String> patterns = new HashMap<>();
		patterns.put(Pattern.compile("\\{W\\}"), "<i class=\"mi mi-w mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{U\\}"), "<i class=\"mi mi-u mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{B\\}"), "<i class=\"mi mi-b mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{R\\}"), "<i class=\"mi mi-r mi-mana mi-shadow\"></i>");
		patterns.put(Pattern.compile("\\{G\\}"), "<i class=\"mi mi-g mi-mana mi-shadow\"></i>");

		for (int i = 0; i <= 20; i++) {
			patterns.put(Pattern.compile("\\{" + i + "\\}"),
					"<i class=\"mi mi-" + i + " mi-mana mi-shadow\"></i>");
		}
		return patterns;
	}

	public String text(Card card) {
		String text = card.getText();
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

}
