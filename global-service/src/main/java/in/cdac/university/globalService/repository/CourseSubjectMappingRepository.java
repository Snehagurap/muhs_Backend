package in.cdac.university.globalService.repository;



import in.cdac.university.globalService.entity.GmstCourseSubjectDtl;
import in.cdac.university.globalService.entity.GmstCourseSubjectDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSubjectMappingRepository extends JpaRepository<GmstCourseSubjectDtl, GmstCourseSubjectDtlPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_course_subject_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();


    List<GmstCourseSubjectDtl> findByUnumCourseIdAndUnumIsvalidAndUnumUnivId(Long unumCourseId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstCourseSubjectDtl u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.unumCourseId = :courseId " +
            "and u.unumSubId in (:subjectIds)")
    Integer delete(@Param("courseId") Long courseId, @Param("subjectIds") List<Long> subjectIds);
}
