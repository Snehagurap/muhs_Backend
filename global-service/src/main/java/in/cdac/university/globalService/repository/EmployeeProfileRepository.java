package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpProfileDtl;
import in.cdac.university.globalService.entity.GmstEmpProfileDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<GmstEmpProfileDtl, GmstEmpProfileDtlPK> {

    List<GmstEmpProfileDtl> findByUnumIsvalidAndUnumUnivIdAndUnumFacultyIdIn(Integer unumIsvalid, Integer unumUnivId, List<Integer> unumFacultyId);

    @Query("select count(*) from GmstEmpProfileDtl " +
            "where unumEmpId= :empID")
    Long getMaxEmpProfileId(@Param("empID") Long empID);

    List<GmstEmpProfileDtl> findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumEmpIds, Collection<Integer> unumIsvalids, Integer unumUnivId);

    List<GmstEmpProfileDtl> findByUnumIsvalidAndUnumEmpId(Integer unumIsvalid, Long unumEmpId);


    @Modifying(clearAutomatically = true)
    @Query("update GmstEmpProfileDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstEmpProfileDtl a where a.unumEmpId = u.unumEmpId and a.unumIsvalid > 2) " +
            "where u.unumEmpId in (:empId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("empId") List<Long> empId);
}
