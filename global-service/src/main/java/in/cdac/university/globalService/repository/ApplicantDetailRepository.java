package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantDtl;
import in.cdac.university.globalService.entity.GmstApplicantDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantDetailRepository extends JpaRepository<GmstApplicantDtl, GmstApplicantDtlPK> {
    List<GmstApplicantDtl> findByUnumApplicantIdAndUnumIsvalid(Long unumApplicantId, Integer unumIsvalid);


}
