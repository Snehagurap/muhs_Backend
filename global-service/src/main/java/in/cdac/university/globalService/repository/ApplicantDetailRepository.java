package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantDtl;
import in.cdac.university.globalService.entity.GmstApplicantDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantDetailRepository extends JpaRepository<GmstApplicantDtl, GmstApplicantDtlPK> {

    @Query ("select count(*) from GmstApplicantDtl " +
            "where unumApplicantId = :applicantId")
    Long getMaxApplicantDocId(@Param("applicantId") Long applicantId);

    List<GmstApplicantDtl> findByUnumApplicantIdAndUnumIsvalid(Long unumApplicantId, Integer unumIsvalid);


    @Modifying(clearAutomatically = true)
    @Query("update GmstApplicantDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 1) + 1 " +
            "from GmstApplicantDtl a where a.unumApplicantId = u.unumApplicantId and a.unumIsvalid > 1) " +
            "where u.unumApplicantId in (:applicantId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("applicantId") List<Long> applicantId);
}


