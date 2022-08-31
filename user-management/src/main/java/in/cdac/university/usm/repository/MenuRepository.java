package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtMenuMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<UmmtMenuMst, Integer> {

    List<UmmtMenuMst> findByGnumModuleIdAndGnumIsvalid(Integer moduleId, Integer isValid);

    List<UmmtMenuMst> findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalid(Integer level, Integer moduleId, Integer isValid);

    Optional<UmmtMenuMst> findByGnumMenuIdAndGnumIsvalidIn(Integer menuId, List<Integer> isValid);

    Optional<UmmtMenuMst> findByGstrMenuNameIgnoreCaseAndGnumIsvalidIn(String menuName, List<Integer> isValid);

    Optional<UmmtMenuMst> findByGstrMenuNameIgnoreCaseAndGnumMenuIdNotAndGnumIsvalidIn(String menuName, Integer menuId, List<Integer> isValid);

}
