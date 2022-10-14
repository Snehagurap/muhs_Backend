package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.entity.GmstApplicantMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<GmstApplicantMst, GmstApplicantMstPK> {
}
