package tcg.db.dbo;

import java.util.Arrays;
import java.util.List;

public enum CardLayout {
	aftermath,
	augment,
	doublefaced,
	doublefacedtoken,
	emblem,
	flip,
	host,
	leveler,
	meld,
	normal,
	phenomenon,
	planar,
	plane,
	saga,
	scheme,
	split,
	vanguard,
	token,
	transform;
	private static final List<CardLayout> CONTCAT_NAMES = Arrays.asList(CardLayout.doublefaced, CardLayout.aftermath, CardLayout.split);
	private static final List<CardLayout> LINK = Arrays.asList(CardLayout.doublefaced,
			CardLayout.meld, CardLayout.aftermath, CardLayout.split);

	public static List<CardLayout> links() {
		return LINK;
	}

	public static List<CardLayout> concatNames() {
		return CONTCAT_NAMES;
	}
}
