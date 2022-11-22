package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.entity.GmstApplicantMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<GmstApplicantMst, GmstApplicantMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_applicant_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GmstApplicantMst> findByUnumIsVerifiedApplicantAndUnumIsvalid(Integer unumIsVerifiedApplicant, Integer unumIsvalid);

    Optional<GmstApplicantMst> findByUnumApplicantIdAndUnumIsvalid(Long unumApplicantId, Integer unumIsvalid);

    @Modifying(clearAutomatically = true)
    @Query("update GmstApplicantMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstApplicantMst a where a.unumApplicantId = u.unumApplicantId and a.unumIsvalid > 2) " +
            "where u.unumApplicantId in (:applicantId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("applicantId") List<Long> applicantId);
}
