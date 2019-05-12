package tcg.db.dao.frag;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AdmFragDao {

	@Update("UPDATE  deck_entry AS e "
			+ "INNER JOIN card  AS o ON e.card = o.id   AND o.edition = #{old} "
			+ "INNER JOIN card  AS n ON n.card = o.card AND n.edition <> #{old} "
			+ "SET e.card = n.id")
	int updateDeckEntry(@Param("old") String deleted);

}
