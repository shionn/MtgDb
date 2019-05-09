package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tcg.db.dbo.Edition;

public interface AdmEditionDao {

	@Select("SELECT * " //
			+ "FROM edition " //
			+ "WHERE update_date IS null OR update_date < NOW() - INTERVAL 7 DAY " //
			+ "ORDER BY name ASC")
	List<Edition> listDeletable();

	@Select("SELECT * " //
			+ "FROM edition " //
			+ "WHERE update_date > NOW() - INTERVAL 7 DAY " //
			+ "ORDER BY name ASC")
	List<Edition> listReplacable();

	@Update("UPDATE  deck_entry AS e "
			+ "INNER JOIN card  AS o ON e.card = o.id   AND o.edition = #{old} "
			+ "INNER JOIN card  AS n ON n.card = o.card AND n.edition = #{new} "
			+ "SET e.card = n.id")
	int updateDeckEntry(@Param("old") String deleted, @Param("new") String replaced);

	@Delete("DELETE FROM card_assistance WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardAssistance(String code);

	@Delete("DELETE FROM card_lang WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardLang(String code);

	@Delete("DELETE FROM card_legality WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardLegality(String code);

	@Delete("DELETE FROM card_price WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardPrice(String code);

	@Delete("DELETE FROM card_rule WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardRule(String code);

	@Delete("DELETE FROM card_type WHERE id IN (SELECT id FROM card WHERE edition = #{id})")
	int deleteCardType(String code);

	@Delete("DELETE FROM card WHERE edition = #{id}")
	int deleteCard(String code);

	@Delete("DELETE FROM edition WHERE code = #{id}")
	int deleteEdition(String code);

}
