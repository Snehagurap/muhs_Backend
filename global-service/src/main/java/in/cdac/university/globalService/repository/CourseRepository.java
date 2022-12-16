package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCourseMst;
import in.cdac.university.globalService.entity.GmstCourseMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<GmstCourseMst, GmstCourseMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_course_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstCourseMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrCourseFname ")
    List<GmstCourseMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    @Query("select c from GmstCourseMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrCourseFname ")
    List<GmstCourseMst> getAllCourses(@Param("universityId") int universityId);

    List<GmstCourseMst> findByUnumIsvalidInAndUnumUnivIdAndUstrCourseFnameIgnoreCase(Collection<Integer> unumIsvalids, Integer unumUnivId, String ustrCourseFname);

    List<GmstCourseMst> findByUnumIsvalidInAndUnumUnivIdAndUstrCourseFnameIgnoreCaseAndUnumCourseIdNot(Collection<Integer> unumIsvalids, Integer unumUnivId, String ustrCourseFname, Long unumCourseId);

    Optional<GmstCourseMst> findByUnumIsvalidInAndUnumUnivIdAndUnumCourseId(Collection<Integer> unumIsvalids, Integer unumUnivId, Long unumCourseId);

    List<GmstCourseMst> findByUnumIsvalidInAndUnumUnivIdAndUnumCourseIdIn(Collection<Integer> unumIsvalids, Integer unumUnivId, List<Long> unumCourseId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstCourseMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstCourseMst a where a.unumCourseId = u.unumCourseId and a.unumIsvalid > 2) " +
            "where u.unumCourseId in (:courseId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("courseId") List<Long> courseId);

    List<GmstCourseMst> findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(Integer unumCfacultyId, Integer unumIsvalid, Integer unumUnivId);

    @Query("select c from GmstCourseMst c " +
            "where c.unumCtypeId in (:courseTypeIds) " +
            "and c.unumIsvalid = :unumIsvalid " +
            "and c.unumUnivId = :universityId")
    List<GmstCourseMst> getMinReqCourseByCourseType(@Param("courseTypeIds") Integer[] courseTypeIds, @Param("unumIsvalid") Integer unumIsvalid, @Param("universityId") Integer universityId);
}
