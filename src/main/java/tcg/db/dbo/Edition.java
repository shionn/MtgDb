package tcg.db.dbo;

import java.util.Date;

public class Edition {

	private String code;
	private String name;
	private Date releaseDate;
	private String mkmName;
	private String goldfishName;
	private int mkmId;
	private boolean onlineOnly;
	private String keyruneCode;
	private EditionType type;

	public String getIcon() {
		return keyruneCode == null ? null : keyruneCode.toLowerCase();
	}

	public String getIconClass() {
		return "ss ss-" + getIcon() + (type == EditionType.promo ? " ss-foil ss-grad" : "");
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setKeyruneCode(String keyruneCode) {
		this.keyruneCode = keyruneCode;
	}

	public String getKeyruneCode() {
		return keyruneCode;
	}

	public EditionType getType() {
		return type;
	}
}
