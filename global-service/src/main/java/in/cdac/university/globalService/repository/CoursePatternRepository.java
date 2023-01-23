package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateItemApiMst;
import in.cdac.university.globalService.entity.GmstCoursePatternDtl;
import in.cdac.university.globalService.entity.GmstCoursePatternDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursePatternRepository extends JpaRepository<GmstCoursePatternDtl, GmstCoursePatternDtlPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_course_pattern_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstCoursePatternDtl c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "order by c.unumCoursePatId")
    List<GmstCoursePatternDtl> getAllCoursePatterns();

    Optional<GmstCoursePatternDtl> findByUnumIsvalidAndUnumCoursePatId(Integer unumIsvalid, Long coursePatId);

    List<GmstCoursePatternDtl> findByUnumCourseIdAndUstrCoursePatFnameIgnoreCaseAndUnumIsvalid(Long unumCourseId, String ustrCoursePatFname, Integer unumIsvalid);

    @Query("select a from GmstCoursePatternDtl a " +
            "where a.unumIsvalid = 1 " +
            "order by a.ustrCoursePatFname ")
    List<GmstConfigTemplateItemApiMst> listPageData();

    @Modifying(clearAutomatically = true)
    @Query("update GmstCoursePatternDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstCoursePatternDtl a where a.unumCoursePatId = u.unumCoursePatId and a.unumIsvalid > 2)," +
            "u.udtEffTo = now() " +
            "where u.unumCoursePatId in (:coursePatId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("coursePatId") List<Long> coursePatId);

    List<GmstCoursePatternDtl> findByUnumIsvalidAndUnumCoursePatIdIn(Integer unumIsvalid, List<Long> idsToDelete);
}
