package tcg.db.dbo;

import java.util.Arrays;

public enum GuildColor {
	white("W"), blue("U"), black("B"), red("R"), green("G"), //
	azorius("WU"), dimir("UB"), rakdos("BR"), gruul("RG"), selesnya("WG"), //
	orzhov("WB"), golgari("BG"), simic("UG"), izzet("UR"), boros("WR"), //

	none("");

	private String bdd;

	GuildColor(String bdd) {
		this.bdd = bdd;

	}
	public static GuildColor from(String color) {
		return Arrays.stream(values()).filter(g -> g.bdd.equals(color)).findFirst().orElse(none);
	}

}
