package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantDraftMst;
import in.cdac.university.globalService.entity.GmstApplicantDraftMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DraftApplicantRepository extends JpaRepository<GmstApplicantDraftMst, GmstApplicantDraftMstPK> {

    @Query(value = "select nextval('university.seq_gmst_applicant_draft_mst')", nativeQuery = true)
    Long getNextId();

    List<GmstApplicantDraftMst> findByUnumIsvalidAndUnumApplicantMobileAndUstrApplicantEmail(Integer unumIsvalid, Long unumApplicantMobile, String ustrApplicantEmail);

    Optional<GmstApplicantDraftMst> findByUnumIsvalidInAndUnumApplicantDraftid(List<Integer> unumIsvalid, Long unumApplicantDraftid);

    @Modifying(clearAutomatically = true)
    @Query("update GmstApplicantDraftMst set " +
            "ustrTempUid = :username, " +
            "ustrPlainPass = :plainPassword, " +
            "ustrTempPass = :password," +
            "ustrGeneratedEmailotp = null, " +
            "ustrGeneratedMotp = null, " +
            "unumIsvalid = 1 " +
            "where unumApplicantDraftid = :draftApplicantId " +
            "and unumIsvalid = 2")
    int updateDraftApplicantUsernameAndPassword(@Param("username") String username,
                                                @Param("plainPassword") String plainPassword,
                                                @Param("password") String password,
                                                @Param("draftApplicantId") Long draftApplicantId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstApplicantDraftMst set " +
            "unumIsvalid = :isValid " +
            "where unumApplicantDraftid = :draftApplicantId " +
            "and unumIsvalid = 1")
    int updateDraftApplicantIsValid(@Param("draftApplicantId") Long draftApplicantId,
                                    @Param("isValid") Integer isValid);
}
