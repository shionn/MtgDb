package tcg.mtgjson.v4.api;

import tcg.db.dbo.GameLegality;

public class MtgJsonLegalities {
	private GameLegality commander;
	private GameLegality duel;
	private GameLegality legacy;
	private GameLegality modern;
	private GameLegality standard;
	private GameLegality vintage;

	public GameLegality getCommander() {
		return commander;
	}

	public void setCommander(GameLegality commander) {
		this.commander = commander;
	}

	public GameLegality getDuel() {
		return duel;
	}

	public void setDuel(GameLegality duel) {
		this.duel = duel;
	}

	public GameLegality getLegacy() {
		return legacy;
	}

	public void setLegacy(GameLegality legacy) {
		this.legacy = legacy;
	}

	public GameLegality getModern() {
		return modern;
	}

	public void setModern(GameLegality modern) {
		this.modern = modern;
	}

	public GameLegality getStandard() {
		return standard;
	}

	public void setStandard(GameLegality standard) {
		this.standard = standard;
	}

	public GameLegality getVintage() {
		return vintage;
	}

	public void setVintage(GameLegality vintage) {
		this.vintage = vintage;
	}
}
