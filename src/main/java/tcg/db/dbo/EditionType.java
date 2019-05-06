package tcg.db.dbo;


public enum EditionType {
	archenemy("Archenemy"),
	box("Boite"),
	core(""),
	commander("Commander"),
	draftinnovation("Draft"),
	dueldeck("Duel Deck"),
	expansion(""),
	fromthevault("From the Vault"),
	funny("Unglued"),
	planechase("Planechase"),
	premiumdeck("Premiun Deck"),
	promo(""),
	masterpiece("Master Piece"),
	masters("Master"),
	memorabilia(""),
	spellbook("Spell Book"),
	starter("Starters"),
	token(""),
	treasurechest(""),
	vanguard("Vanguard");
	private String displayName;

	private EditionType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
