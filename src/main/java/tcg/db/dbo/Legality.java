package tcg.db.dbo;

public class Legality {

	private String format;
	private GameLegality legality;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setLegality(GameLegality legality) {
		this.legality = legality;
	}

	public GameLegality getLegality() {
		return legality;
	}

	@Override
	public String toString() {
		return "Legality [format=" + format + ", legality=" + legality + "]";
	}

}
