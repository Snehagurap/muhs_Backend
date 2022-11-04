package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.GmstApplicantMst;
import in.cdac.university.authserver.entity.GmstApplicantMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<GmstApplicantMst, GmstApplicantMstPK> {

    Optional<GmstApplicantMst> findByUnumIsvalidAndUstrUid(Integer unumIsvalid, String ustrUid);

}
