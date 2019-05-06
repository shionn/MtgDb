package tcg.db.dbo;

import java.util.Arrays;
import java.util.List;

public enum CardLayout {
	aftermath,
	augment,
	doublefaced, // TODO semble ne plus existé
	doublefacedtoken,
	emblem,
	flip,
	host,
	leveler,
	meld,
	normal,
	phenomenon, // TODO semble ne plus existé
	planar,
	plane, // TODO semble ne plus existé
	saga,
	scheme,
	split,
	vanguard,
	token,
	transform;
	private static final List<CardLayout> MKM_CONCAT_NAMES = Arrays.asList(doublefaced, aftermath,
			split, transform);
	private static final List<CardLayout> LINK = Arrays.asList(doublefaced, aftermath, split, flip,
			transform);

	public boolean isLink() {
		return LINK.contains(this);
	}

	public static List<CardLayout> links() {
		return LINK;
	}

	public boolean isMkmConcatName() {
		return MKM_CONCAT_NAMES.contains(this);
	}
}
