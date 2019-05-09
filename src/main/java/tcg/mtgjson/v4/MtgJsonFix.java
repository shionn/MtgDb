package tcg.mtgjson.v4;

import java.util.function.Consumer;

import tcg.mtgjson.v4.api.MtgJsonSet;

public class MtgJsonFix {

	enum EditionFix {
		PVAN(set -> set.setMcmName("Vanguard")), TSB(set -> set.setMcmName("Time Spiral"));
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
}
