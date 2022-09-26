package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCourseDurationCatMst;
import in.cdac.university.globalService.entity.GmstCourseDurationCatMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDurationCategoryRepository extends JpaRepository<GmstCourseDurationCatMst, GmstCourseDurationCatMstPK> {

    @Query("select c from GmstCourseDurationCatMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrCdCategoryFname ")
    List<GmstCourseDurationCatMst> allCourseDurationCategories(@Param("universityId") int universityId);
}
