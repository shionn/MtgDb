package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Edition;
import tcg.db.dbo.EditionGroup;
import tcg.db.dbo.EditionType;

public interface EditionListDao {

	@Select("SELECT * FROM edition ORDER BY release_date DESC")
	List<Edition> listEditions();

	@Select("SELECT block, IFNULL(block, 'Others') AS name, "
			+ "min(release_date) AS release_date, type " //
			+ "FROM edition " //
			+ "WHERE type = #{type} " //
			+ "GROUP BY block " //
			+ "ORDER BY release_date DESC")
	@Results({
			@Result(column = "{block=block, type=type}", property = "editions", many = @Many(select = "readExpansionEdition")) })
	List<EditionGroup> readBlock(@Param("type") EditionType type);


	@Select("<script>SELECT * FROM edition "
			+ "WHERE "
			+ " ( parent_code IN ( SELECT code FROM edition WHERE block = #{block} AND type = #{type} ) "
			+ "      AND type IN ('promo', 'masterpiece') )"
			+ " OR ( block = #{block} AND type = #{type}) " //
			// + "<if test=\"block == null\">WHERE block IS NULL AND type = #{type} </if>" //
			// + "<if test=\"block != null\">WHERE block = #{block} AND type IN (#{type}, 'promo', 'masterpiece') </if>" //
			+ "ORDER BY release_date DESC, type ASC</script>")
	List<Edition> readExpansionEdition(@Param("block") String block,
			@Param("type") EditionType type);

	@Select("SELECT '${type.displayName}' AS name, type " //
			+ "FROM edition " //
			+ "WHERE type = #{type} " //
			+ "GROUP BY type ")
	@Results({
			@Result(column = "type", property = "editions", many = @Many(select = "readEdition")) })
	EditionGroup readGroup(@Param("type") EditionType type);

	@Select("SELECT * FROM edition " //
			+ "WHERE type = #{type} " //
			+ "   OR parent_code IN (SELECT code FROM edition WHERE type = #{type}) " //
			+ "ORDER BY release_date DESC, type ASC ")
	List<Edition> readEdition(@Param("type") EditionType type);

}
