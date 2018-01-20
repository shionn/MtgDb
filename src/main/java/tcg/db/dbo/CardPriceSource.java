package tcg.db.dbo;

public enum CardPriceSource {
	mkm("mkm.gif", "€"),//
	mkmFoil("mkm.gif", "€"), //
	MtgGoldFishPaper("MtgGoldFish.gif", "$"),
	MtgGoldFishFoilPaper("MtgGoldFish.gif", "$"),
	MtgGoldFishTx("MtgGoldFish.gif", "tx"),
	MtgGoldFishFoilTx("MtgGoldFish.gif", "tx");

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

	public boolean isFoil() {
		return name().indexOf("Foil") != -1;
	}
}
