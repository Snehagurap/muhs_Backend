package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpCurDtl;
import in.cdac.university.globalService.entity.GmstEmpCurDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeCurrentDetailRepository extends JpaRepository<GmstEmpCurDtl, GmstEmpCurDtlPK> {

    List<GmstEmpCurDtl> findByUnumIsvalidAndUnumUnivIdAndUnumEmpDesigidIn(Integer unumIsvalid, Integer unumUnivId, List<Integer> unumDesigid);

    @Query("select count(*) from GmstEmpCurDtl " +
            "where unumEmpId= :empID")
    Long getMaxEmpCurrDetailId(@Param("empID") Long empID);

    Optional<GmstEmpCurDtl> findByUnumIsvalidAndUnumEmpId(Integer unumIsvalid, Long unumEmpId);


}
