package tcg.db.dbo;

import java.util.Arrays;
import java.util.List;

public enum CardLayout {
	normal,
	split,
	flip,
	doublefaced,
	token,
	plane,
	scheme,
	phenomenon,
	leveler,
	vanguard,
	meld,
	aftermath;

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
