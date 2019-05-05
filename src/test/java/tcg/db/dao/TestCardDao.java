package tcg.db.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import tcg.db.dbo.Card;

public interface TestCardDao {

	@Select("SELECT * FROM card")
	Cursor<Card> cards();

}
