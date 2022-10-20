package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantTypeMst;
import in.cdac.university.globalService.entity.GmstApplicantTypeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ApplicantTypeRepository extends JpaRepository<GmstApplicantTypeMst, GmstApplicantTypeMstPK> {

    @Query("select c from GmstApplicantTypeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "order by c.ustrApplicantTypeFname ")
    List<GmstApplicantTypeMst> getActiveApplicantTypes();
}
