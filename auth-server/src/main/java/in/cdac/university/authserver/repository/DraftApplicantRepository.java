package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.GmstApplicantDraftMst;
import in.cdac.university.authserver.entity.GmstApplicantDraftMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DraftApplicantRepository extends JpaRepository<GmstApplicantDraftMst, GmstApplicantDraftMstPK> {

    @Query("select a from GmstApplicantDraftMst a where a.ustrTempUid = ?1 and a.unumIsvalid = ?2")
    Optional<GmstApplicantDraftMst> findUser(String gstrUsername, Integer gnumIsvalid);
}
