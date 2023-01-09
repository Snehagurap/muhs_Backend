package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmstColumnMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColumnRepository extends JpaRepository<UmstColumnMst, String> {

    @Query(value="select column_name as table_column_id , upper(column_name) as table_column_name from information_schema.columns  where upper(table_name) = upper(:tableName)" +
            " and table_name in (SELECT tablename FROM pg_catalog.pg_tables   where schemaname =:schemaName ORDER BY column_name ASC)",nativeQuery = true)
    List<UmstColumnMst> findAllByTableNameAndSchemaName(@Param("tableName") String tableName,
                                                        @Param("schemaName") String schemaName);
}
