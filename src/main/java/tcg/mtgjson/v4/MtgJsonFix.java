package tcg.mtgjson.v4;

import java.util.function.Consumer;

import tcg.mtgjson.v4.api.MtgJsonSet;

public class MtgJsonFix {
	private static final String DCI_LOGO = "parl";

	@SuppressWarnings("unchecked")
	enum EditionFix {
		F04(mcmName("Friday Night Magic Promos"),keyrune(DCI_LOGO)), //
		G03(mcmName("Judge Rewards Promos"), keyrune(DCI_LOGO)), //
		J18(mcmName("Judge Rewards Promos"), keyrune("pmei")), //
		ME1(online()), //
		PAL00(mcmName("Arena League Promos")), //
		PFNM(keyrune(DCI_LOGO)), //
		PRM(online(), keyrune("pmodo")), //
		PVAN(mcmName("Vanguard")), //
		PZ1(online()), //
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
