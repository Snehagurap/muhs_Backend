package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtRoleMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UmmtRoleMst, Integer> {

    List<UmmtRoleMst> findByGblIsvalidOrderByGstrRoleNameAsc(Integer gblIsvalid);

}
