package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtRoleMenuMst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMenuRepository extends JpaRepository<UmmtRoleMenuMst, Integer> {

    List<UmmtRoleMenuMst> findByGnumRoleIdAndGnumModuleId(Integer roleId, Integer moduleId);

    List<UmmtRoleMenuMst> findByGnumMenuIdIn(List<Integer> menuIds);

    List<UmmtRoleMenuMst> findByGnumRoleIdInAndGnumIsvalidOrderByGnumDisplayOrder(List<Integer> gnumRoleId, Integer isValid);


}
