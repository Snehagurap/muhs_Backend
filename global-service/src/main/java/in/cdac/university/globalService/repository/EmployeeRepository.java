package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.entity.GmstEmpMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<GmstEmpMst, GmstEmpMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_emp_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstEmpMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrEmpName ")
    List<GmstEmpMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    List<GmstEmpMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);

    Optional<GmstEmpMst> findByUnumEmpIdAndUnumIsvalidInAndUnumUnivId(Long unumEmpId, Collection<Integer> unumIsvalids, Integer unumUnivId);

    List<GmstEmpMst> findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumEmpIds, Collection<Integer> unumIsvalids, Integer unumUnivId);

    List<GmstEmpMst> findByUnumIsvalidInAndUnumUnivIdAndUstrTPanNo(Collection<Integer> isValid,Integer universityId,String panNo);

    @Modifying(clearAutomatically = true)
    @Query("update GmstEmpMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstEmpMst a where a.unumEmpId = u.unumEmpId and a.unumIsvalid > 2) " +
            "where u.unumEmpId in (:empId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("empId") List<Long> empId);

    @Modifying
    @Query("update GmstEmpMst u set u.unumTotLicChairmanCount = coalesce(u.unumTotLicChairmanCount, 0) + 1, " +
            "u.unumAyLicChairmanCount = coalesce(u.unumAyLicChairmanCount, 0) + 1 " +
            "where u.unumIsvalid = 1 " +
            "and u.unumEmpId in (:empList) ")
    Integer updateChairmanFlag(@Param("empList") List<Long> employeeList);

    @Modifying
    @Query("update GmstEmpMst u set u.unumTotLicMember1Count = coalesce(u.unumTotLicMember1Count, 0) + 1, " +
            "u.unumAyLicMember1Count = coalesce(u.unumAyLicMember1Count, 0) + 1 " +
            "where u.unumIsvalid = 1 " +
            "and u.unumEmpId in (:empList) ")
    Integer updateMember1Flag(@Param("empList") List<Long> employeeList);

    @Modifying
    @Query("update GmstEmpMst u set u.unumTotLicMember2Count = coalesce(u.unumTotLicMember2Count, 0) + 1, " +
            "u.unumAyLicMember2Count = coalesce(u.unumAyLicMember2Count, 0) + 1 " +
            "where u.unumIsvalid = 1 " +
            "and u.unumEmpId in (:empList) ")
    Integer updateMember2Flag(@Param("empList") List<Long> employeeList);


    @Modifying
    @Query("update GmstEmpMst u set u.unumIsSelected = 1 " +
            "where u.unumIsvalid = 1 " +
            "and u.unumEmpId in (:empList) ")
    Integer updateCommitteeSelectionFlag(@Param("empList") List<Long> employeeList);

     GmstEmpMst findByUnumEmpIdAndUnumIsvalidAndUnumUnivId(Long unumEmpId, Integer unumIsvalid, Integer unumUnivId);

}
