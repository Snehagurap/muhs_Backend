package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtRoleMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UmmtRoleMst, Integer> {

    List<UmmtRoleMst> findByGblIsvalidOrderByGstrRoleNameAsc(Integer gblIsvalid);

    Optional<UmmtRoleMst> findByGstrRoleNameIgnoreCaseAndGblIsvalidNot(String roleName, Integer isValid);

    Optional<UmmtRoleMst> findByGnumRoleIdAndGblIsvalidNot(Integer roleId, Integer isValid);

    List<UmmtRoleMst> findAllByGnumRoleIdInAndGblIsvalidNot(List<Integer> roleId, Integer isValid);

    List<UmmtRoleMst> findByModuleGnumModuleIdAndGblIsvalid(Integer moduleId, Integer isValid);

    Optional<UmmtRoleMst> findByGstrRoleNameIgnoreCaseAndGnumRoleIdNotAndGblIsvalidNot(String roleName, Integer roleId, Integer isValid);

}
