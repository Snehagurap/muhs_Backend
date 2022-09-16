package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCollegeMst;
import in.cdac.university.globalService.entity.GmstCollegeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<GmstCollegeMst, GmstCollegeMstPK> {

    @Query("select c from GmstCollegeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by ustrColFname ")
    List<GmstCollegeMst> findColleges(Integer universityId);

}
