package tcg.db.dbo;

public enum CardRarity {
	common("common"),
	uncommon("uncommon"),
	rare("rare"),
	mythic("mythic"),
	mythicrare("mythic"), // concervé pour compatibilité avec l'ancienne version de mtgjson
	special("timeshifted"),
	basicland("common");
	private String ss;

	private CardRarity(String ss) {
		this.ss = ss;
	}

	public String getSs() {
		return ss;
	}
}
