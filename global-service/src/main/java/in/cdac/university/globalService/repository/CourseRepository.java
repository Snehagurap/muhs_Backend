package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCourseMst;
import in.cdac.university.globalService.entity.GmstCourseMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<GmstCourseMst, GmstCourseMstPK> {

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
}
