package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstDropdownMst;
import in.cdac.university.globalService.entity.GmstDropdownMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropdownRepository extends JpaRepository<GmstDropdownMst, GmstDropdownMstPK> {
    List<GmstDropdownMst> findByUnumIsvalidAndUnumDropdownIdOrderByUnumOrderNoAsc(Integer unumIsvalid, Long unumDropdownId);

}
