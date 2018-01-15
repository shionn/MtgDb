package tcg.db.dbo;

import java.util.Date;

public class Edition {
	private String code;
	private String magicCardInfoCode;
	private String name;
	private Date realeaseDate;

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
}
