package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantDtl;
import in.cdac.university.globalService.entity.GmstApplicantDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantDetailRepository extends JpaRepository<GmstApplicantDtl, GmstApplicantDtlPK> {

    @Query ("select count(*) from GmstApplicantDtl " +
            "where unumApplicantId = :applicantId")
    Long getMaxApplicantDocId(@Param("applicantId") Long applicantId);
}
