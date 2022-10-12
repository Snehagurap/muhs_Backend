package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.CmstCollegeCourseMst;
import in.cdac.university.globalService.entity.CmstCollegeCourseMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeCourseMappingRepository extends JpaRepository<CmstCollegeCourseMst, CmstCollegeCourseMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_cmst_college_course_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<CmstCollegeCourseMst> findByUnumIsvalidAndUnumUnivIdAndUnumCfacultyIdAndUnumCollegeId(Integer unumIsvalid, Integer unumUnivId, Integer unumCfacultyId, Long unumCollegeId);

    @Modifying(clearAutomatically = true)
    @Query("update CmstCollegeCourseMst u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.unumCollegeId = :collegeId and unumCfacultyId = :facultyId " +
            "and u.unumCourseId in (:courseIds)")
    Integer delete(@Param("collegeId") Long collegeId, @Param("facultyId") Integer facultyId, @Param("courseIds") List<Long> courseIds);
}
