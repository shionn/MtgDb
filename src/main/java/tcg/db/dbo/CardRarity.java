package tcg.db.dbo;

public enum CardRarity {
	common("common"), uncommon("uncommon"), rare("rare"), mythicrare("mythic"), special("special"), basicland("common");
	private String ss;

	private CardRarity(String ss) {
		this.ss = ss;
	}

	public String getSs() {
		return ss;
	}
}
