package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpCurDtl;
import in.cdac.university.globalService.entity.GmstEmpCurDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeCurrentDetailRepository extends JpaRepository<GmstEmpCurDtl, GmstEmpCurDtlPK> {

    List<GmstEmpCurDtl> findByUnumIsvalidAndUnumUnivIdAndUnumEmpDesigidIn(Integer unumIsvalid, Integer unumUnivId, List<Integer> unumDesigid);

}
