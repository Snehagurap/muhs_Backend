package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmstModuleMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<UmstModuleMst, Integer> {

    List<UmstModuleMst> findAllByGblIsvalid(Integer gnumIsvalid);
}
