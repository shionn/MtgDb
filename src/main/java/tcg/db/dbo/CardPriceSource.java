package tcg.db.dbo;

public enum CardPriceSource {
	mkm("mkm.gif", "€"), MtgGoldFishPaper("MtgGoldFish.gif", "$"), MtgGoldFishTx("MtgGoldFish.gif", "t");

	private String icon;
	private String currency;

	private CardPriceSource(String icon, String currency) {
		this.icon = icon;
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public String getIcon() {
		return icon;
	}
}
