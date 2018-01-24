package tcg.trade;

import java.math.BigDecimal;

public class TradTotal {

	private BigDecimal mkm;
	private BigDecimal mtgGoldFishTx;
	private BigDecimal mtgGoldFish;

	public TradTotal(BigDecimal mkm, BigDecimal mtgGoldFish, BigDecimal mtgGoldFishTx) {
		this.mkm = mkm;
		this.mtgGoldFish = mtgGoldFish;
		this.mtgGoldFishTx = mtgGoldFishTx;
	}

	public BigDecimal getMkm() {
		return mkm;
	}

	public BigDecimal getMtgGoldFish() {
		return mtgGoldFish;
	}

	public BigDecimal getMtgGoldFishTx() {
		return mtgGoldFishTx;
	}

}
