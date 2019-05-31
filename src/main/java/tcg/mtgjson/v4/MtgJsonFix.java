package tcg.mtgjson.v4;

import java.util.function.Consumer;

import tcg.mtgjson.v4.api.MtgJsonSet;

public class MtgJsonFix {
	private static final String DCI_LOGO = "parl";
	private static final String STAR_LOGO = "pmei";
	private static final String DO_NOT_CRAWL = "DO_NOT_CRAWL";

	@SuppressWarnings("unchecked")
	enum EditionFix {
		F04(mcmName("Friday Night Magic Promos"),keyrune(DCI_LOGO)), //
		G00(keyrune(DCI_LOGO)), //
		G01(keyrune(DCI_LOGO)), //
		G02(keyrune(DCI_LOGO)), //
		G03(mcmName("Judge Rewards Promos"), keyrune(DCI_LOGO)), //
		G04(keyrune(DCI_LOGO)), //
		G06(keyrune(DCI_LOGO)), //
		G07(keyrune(DCI_LOGO)), //
		G08(keyrune(DCI_LOGO)), //
		G09(keyrune(DCI_LOGO)), //
		G10(keyrune(DCI_LOGO)), //
		G11(keyrune(DCI_LOGO)), //
		HHO(keyrune(STAR_LOGO)), //
		J12(keyrune(STAR_LOGO)), //
		J13(keyrune(STAR_LOGO)), //
		J14(keyrune(STAR_LOGO)), //
		J15(keyrune(STAR_LOGO)), //
		J16(keyrune(STAR_LOGO)), //
		J17(keyrune(STAR_LOGO)), //
		J18(mcmName("Judge Rewards Promos"), keyrune(STAR_LOGO)), //
		J19(keyrune(STAR_LOGO)), //
		ME1(online()), //
		PAL00(mcmName("Arena League Promos")), //
		PFNM(keyrune(DCI_LOGO)), //
		PMOA(mcmName(DO_NOT_CRAWL), goldFishName(DO_NOT_CRAWL)), //
		PRM(online(), keyrune("pmodo")), //
		PSAL(mcmName(DO_NOT_CRAWL), goldFishName(DO_NOT_CRAWL)),
		PVAN(mcmName("Vanguard")), //
		PS11(mcmName("Salvat-Hachette 2011")), //
		PZ1(online()), //
		PZ2(online()), //
		TPR(online()), //
		TSB(mcmName("Time Spiral")), //
		VMA(online()),
		WC00(mcmName("WCD 2000: Tom Van de Logt|WCD 2000: Jon Finkel")),//
		;
		private Consumer<MtgJsonSet>[] fixs;

		private EditionFix(Consumer<MtgJsonSet>... fixs) {
			this.fixs = fixs;
		}
	}

	MtgJsonSet fix(MtgJsonSet set) {
		try {
			EditionFix editionFix = EditionFix.valueOf(set.getCode());
			for (Consumer<MtgJsonSet> fix : editionFix.fixs) {
				fix.accept(set);
			}
		} catch (IllegalArgumentException e) {
			// rien à faire
		}
		return set;
	}

	static private Consumer<MtgJsonSet> goldFishName(String name) {
		return e -> e.setGoldfishName(name);
	}

	static private Consumer<MtgJsonSet> mcmName(String name) {
		return set -> set.setMcmName(name);
	}

	static private Consumer<MtgJsonSet> keyrune(String name) {
		return set -> set.setKeyruneCode(name);
	}

	static private Consumer<MtgJsonSet> online() {
		return set -> set.setOnlineOnly(true);
	}
}
