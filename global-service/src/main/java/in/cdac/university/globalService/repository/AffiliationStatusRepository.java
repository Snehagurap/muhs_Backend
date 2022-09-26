package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstAffiliationstatusMst;
import in.cdac.university.globalService.entity.GmstAffiliationstatusMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffiliationStatusRepository extends JpaRepository<GmstAffiliationstatusMst, GmstAffiliationstatusMstPK> {

    @Query("select a from GmstAffiliationstatusMst a " +
            "where a.unumIsvalid = 1 " +
            "and a.udtEffFrom <= current_date " +
            "and coalesce(a.udtEffTo, current_date) >= current_date " +
            "order by ustrAffFtatusFname ")
    List<GmstAffiliationstatusMst> getAffiliationStatus();
}
