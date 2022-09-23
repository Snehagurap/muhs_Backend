package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtMenuMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<UmmtMenuMst, Integer> {

    List<UmmtMenuMst> findByGnumModuleIdAndGnumIsvalid(Integer moduleId, Integer isValid);

    Optional<UmmtMenuMst> findByGnumModuleIdAndRootMenuIdAndGnumIsvalidAndGnumParentId(Integer moduleId, Integer rootMenuId, Integer isValid, Integer parentId);

    List<UmmtMenuMst> findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalidAndRootMenuId(Integer level, Integer moduleId, Integer isValid, Integer rootMenuId);

    List<UmmtMenuMst> findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalidAndRootMenuIdAndGnumParentId(Integer level, Integer moduleId, Integer isValid, Integer rootMenuId, Integer parentMenuId);

    Optional<UmmtMenuMst> findByGnumMenuIdAndGnumIsvalidIn(Integer menuId, List<Integer> isValid);

    List<UmmtMenuMst> findByGnumMenuIdInAndGnumIsvalid(List<Integer> menuId, Integer isValid);

    Optional<UmmtMenuMst> findByGstrMenuNameIgnoreCaseAndGnumModuleIdAndGnumIsvalidIn(String menuName, Integer moduleId, List<Integer> isValid);

    Optional<UmmtMenuMst> findByGstrMenuNameIgnoreCaseAndGnumMenuIdNotAndGnumIsvalidIn(String menuName, Integer menuId, List<Integer> isValid);

    @Query("select m from UmmtMenuMst m " +
            "where m.gnumIsvalid = 1 " +
            "and (m.gstrUrl = null or trim(m.gstrUrl) = '') ")
    List<UmmtMenuMst> findAllRootMenus();
}
