package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpCurDtl;
import in.cdac.university.globalService.entity.GmstEmpCurDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmployeeCurrentDetailRepository extends JpaRepository<GmstEmpCurDtl, GmstEmpCurDtlPK> {

    List<GmstEmpCurDtl> findByUnumIsvalidAndUnumUnivIdAndUnumEmpDesigidIn(Integer unumIsvalid, Integer unumUnivId, List<Integer> unumDesigid);

    @Query("select count(*) from GmstEmpCurDtl " +
            "where unumEmpId= :empID")
    Long getMaxEmpCurrDetailId(@Param("empID") Long empID);

    Optional<GmstEmpCurDtl> findByUnumIsvalidAndUnumEmpId(Integer unumIsvalid, Long unumEmpId);

    List<GmstEmpCurDtl> findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumEmpIds, List<Integer> unumIsvalid, Integer unumUnivId);



    @Modifying(clearAutomatically = true)
    @Query("update GmstEmpCurDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstEmpCurDtl a where a.unumEmpId = u.unumEmpId and a.unumIsvalid > 2) " +
            "where u.unumEmpId in (:empId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("empId") List<Long> empId);

    List<GmstEmpCurDtl> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);


}

