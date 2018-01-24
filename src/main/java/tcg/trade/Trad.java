package tcg.trade;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;

public class Trad {

	private boolean foil;
	private Card card;

	public Trad(Card card, boolean foil) {
		this.card = card;
		this.foil = foil;
	}

	public Card getCard() {
		return card;
	}

	public boolean isFoil() {
		return foil;
	}

	public CardPrice getMkmPrice() {
		return card.getPrice(foil ? CardPriceSource.mkmFoil : CardPriceSource.mkm);
	}
	public CardPrice getMtgGoldFishPrice() {
		return card.getPrice(foil ? CardPriceSource.MtgGoldFishFoilPaper : CardPriceSource.MtgGoldFishPaper);
	}
	public CardPrice getMtgGoldFishTxPrice() {
		return card.getPrice(foil ? CardPriceSource.MtgGoldFishFoilTx : CardPriceSource.MtgGoldFishTx);
	}
}
