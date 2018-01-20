package tcg.db.dbo;

import java.util.Date;

public class CardRule {

	private Date created;
	private String rule;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRule() {
		return rule;
	}
}
