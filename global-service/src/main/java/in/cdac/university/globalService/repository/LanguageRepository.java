package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstLanguageMst;
import in.cdac.university.globalService.entity.GmstLanguageMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<GmstLanguageMst, GmstLanguageMstPK> {

    @Query("select l from GmstLanguageMst l " +
            "where l.unumIsvalid = 1 " +
            "and l.udtEffFrm <= current_date " +
            "and coalesce(l.udtEffTo, current_date) >= current_date " +
            "and l.unumUnivId = :universityId " +
            "order by l.ustrLangFname")
    List<GmstLanguageMst> getAllLanguages(@Param("universityId") Integer universityId);
}
