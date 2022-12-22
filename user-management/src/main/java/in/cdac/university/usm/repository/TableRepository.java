package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmstTableMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<UmstTableMst, String> {

    @Query(value = "SELECT tablename as tablenameid,upper(tablename) as tablename FROM pg_catalog.pg_tables   where schemaname = :gstrSchemaName order by tablename",nativeQuery = true)
    List<UmstTableMst> findAllOrderByGstrSchemaName(@Param("gstrSchemaName") String gstrSchemaName);

}
