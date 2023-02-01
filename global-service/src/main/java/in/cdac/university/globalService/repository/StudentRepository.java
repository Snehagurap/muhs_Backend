package in.cdac.university.globalService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStudentMst;
import in.cdac.university.globalService.entity.GmstStudentMstPK;


@Repository
public interface StudentRepository extends JpaRepository<GmstStudentMst, GmstStudentMstPK> {

	GmstStudentMst findByUstrEnrollmentNoIgnoreCaseAndUnumUnivIdAndUnumIsvalid(String studentEnrollmentNo,
			Integer universityId, int i);
	
	
    @Modifying(clearAutomatically = true)
    @Query("update GmstStudentMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstStudentMst a where a.unumStudentId = u.unumStudentId and a.unumIsvalid > 2) " +
            "where u.unumStudentId in (:unumStudentId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("unumStudentId") List<Long> unumStudentId);
    
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_student_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

}
