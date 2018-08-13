package tcg.db.dbo;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DeckEntry {

	private int deck;

	private Card card;
	private boolean foil;
	private DeckEntryCategory category;

	private int qty;
	private String tag;

	public boolean isFoil() {
		return foil;
	}

	public void setFoil(boolean foil) {
		this.foil = foil;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public DeckEntryCategory getCategory() {
		return category;
	}

	public void setCategory(DeckEntryCategory category) {
		this.category = category;
	}

	public int getDeck() {
		return deck;
	}

	public void setDeck(int deck) {
		this.deck = deck;
	}

	public List<String> getTags() {
		return Arrays.asList(StringUtils.split(tag == null ? "" : tag, ';'));
	}

	public CardPrice getMkmPrice() {
		return card.getPrice(foil ? CardPriceSource.mkmFoil : CardPriceSource.mkm);
	}

	public CardPrice getMtgGoldFishPrice() {
		return card.getPrice(
				foil ? CardPriceSource.MtgGoldFishFoilPaper : CardPriceSource.MtgGoldFishPaper);
	}

	public CardPrice getMtgGoldFishTxPrice() {
		return card
				.getPrice(foil ? CardPriceSource.MtgGoldFishFoilTx : CardPriceSource.MtgGoldFishTx);
	}

}
