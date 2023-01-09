package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmstDbSchemaMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DBSchemaRepository extends JpaRepository<UmstDbSchemaMst, Integer> {

    List<UmstDbSchemaMst> findAllByGblIsvalidOrderByGstrModuleName(Integer gnumIsvalid);
}
