package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.controller.GmstCourseTypeMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseTypeRepository extends JpaRepository<GmstCourseTypeMst, Long> {

    @Query("select c from GmstCourseTypeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by ustrCtypeFname ")
    List<GmstCourseTypeMst> getAllCourseTypes(@Param("universityId") int universityId);

    List<GmstCourseTypeMst> findByUnumIsvalid(Integer unumIsvalid);

    Optional<GmstCourseTypeMst> findByUnumIsvalidAndUnumCtypeId(Integer unumIsvalid, Long unumCtypeId);

}
