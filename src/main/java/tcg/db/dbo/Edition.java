package tcg.db.dbo;

import java.util.Date;

public class Edition {
	public enum Foil {
		both, onlyfoil, nofoil
	}

	private String code;
	private String icon;
	private String mciCode;
	private String name;
	private Date releaseDate;
	private String mkmName;
	private String goldfishName;
	private int mkmId;
	private boolean onlineOnly;
	private Foil foil;

	public String getIcon() {
		return icon == null ? code.toLowerCase() : icon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMciCode() {
		return mciCode;
	}

	public void setMciCode(String mciCode) {
		this.mciCode = mciCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date realeaseDate) {
		this.releaseDate = realeaseDate;
	}

	public String getGoldfishName() {
		return goldfishName;
	}

	public void setGoldfishName(String goldfishName) {
		this.goldfishName = goldfishName;
	}

	public String getMkmName() {
		return mkmName;
	}

	public void setMkmName(String mkmName) {
		this.mkmName = mkmName;
	}

	public int getMkmId() {
		return mkmId;
	}

	public void setMkmId(int mkmId) {
		this.mkmId = mkmId;
	}

	public boolean isOnlineOnly() {
		return onlineOnly;
	}

	public void setOnlineOnly(boolean onlineOnly) {
		this.onlineOnly = onlineOnly;
	}

	public Foil getFoil() {
		return foil;
	}

	public void setFoil(Foil foil) {
		this.foil = foil;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
