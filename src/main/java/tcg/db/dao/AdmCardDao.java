package tcg.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import tcg.db.dao.frag.AdmFragDao;

public interface AdmCardDao extends AdmFragDao {

	@Delete("DELETE FROM card_assistance WHERE id = #{id}")
	int deleteAssistance(String id);

	@Delete("DELETE FROM card_lang WHERE id = #{id}")
	int deleteLang(String id);

	@Delete("DELETE FROM card_legality WHERE id = #{id}")
	int deleteLegality(String id);

	@Delete("DELETE FROM card_price WHERE id = #{id}")
	int deletePrice(String id);

	@Delete("DELETE FROM card_rule WHERE id = #{id}")
	int deleteRule(String id);

	@Delete("DELETE FROM card_type WHERE id = #{id}")
	int deleteType(String id);

	@Update("UPDATE card SET link_card = NULL WHERE id = #{id}")
	int removeLink(String id);

	@Delete("DELETE FROM card WHERE id = #{id}")
	int delete(String id);



}
