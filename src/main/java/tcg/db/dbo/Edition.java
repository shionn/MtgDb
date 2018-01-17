package tcg.db.dbo;

import java.util.Date;

public class Edition {
	private String code;
	private String magicCardInfoCode;
	private String name;
	private Date realeaseDate;
	private String mkmName;
	private int mkmId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMagicCardInfoCode() {
		return magicCardInfoCode;
	}

	public void setMagicCardInfoCode(String magicCardInfoCode) {
		this.magicCardInfoCode = magicCardInfoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRealeaseDate() {
		return realeaseDate;
	}

	public void setRealeaseDate(Date realeaseDate) {
		this.realeaseDate = realeaseDate;
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
}
