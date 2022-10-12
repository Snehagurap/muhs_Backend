package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.CmstCollegeFacultyMst;
import in.cdac.university.globalService.entity.CmstCollegeFacultyMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeFacultyMappingRepository extends JpaRepository<CmstCollegeFacultyMst, CmstCollegeFacultyMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_cmst_college_faculty_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<CmstCollegeFacultyMst> findByUnumIsvalidAndUnumUnivIdAndUnumCollegeId(Integer unumIsvalid, Integer unumUnivId, Long unumCollegeId);

    @Modifying(clearAutomatically = true)
    @Query("update CmstCollegeFacultyMst u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.unumCollegeId = :collegeId and unumFacultyId in (:facultyIds) ")
    Integer delete(@Param("collegeId") Long collegeId, @Param("facultyIds") List<Integer> facultyIds);
}
