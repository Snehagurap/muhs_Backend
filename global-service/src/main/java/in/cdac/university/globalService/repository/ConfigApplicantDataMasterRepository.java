package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationDataMst;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataMstPK;
import in.cdac.university.globalService.entity.GmstApplicationStatusMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigApplicantDataMasterRepository extends JpaRepository<GbltConfigApplicationDataMst, GbltConfigApplicationDataMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('templedata.seq_gblt_config_application_data_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GbltConfigApplicationDataMst c " +
            "where unumIsvalid = 1 " +
            "and unumUnivId = :universityId " +
            "and unumApplicantId = :applicantId " +
            "and unumNid = :notificationId " +
            "and unumNdtlId = :notificationDetailId ")
    Optional<GbltConfigApplicationDataMst> getApplication(@Param("universityId") Integer universityId,
                                                          @Param("applicantId") Long applicantId,
                                                          @Param("notificationId") Long notificationId,
                                                          @Param("notificationDetailId") Long notificationDetailId);

    @Query("select c from GbltConfigApplicationDataMst c " +
            "where unumIsvalid = 1 " +
            "and unumUnivId = :universityId " +
            "and unumApplicationEntryStatus = :status " +
            "and unumNid = :notificationId " )
    List<GbltConfigApplicationDataMst> getApplicationByNotification(@Param("universityId") Integer universityId,
                                                          @Param("status") Integer notificationStatus,
                                                          @Param("notificationId") Long notificationId);

    @Query("select c from GmstApplicationStatusMst c  where unumIsvalid = 1 ORDER BY unumApplicationStatusId")
    List<GmstApplicationStatusMst> getAllApplicationStatus();

    Optional<GbltConfigApplicationDataMst> findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(Long unumApplicationId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltConfigApplicationDataMst u set unumApplicationEntryStatus = :applicationStatusId " +
            "where unumApplicationId = :applicationId " +
            "and unumApplicantId = :applicantId " +
            "and unumIsvalid = :unumIsvalid")
    void updateApplicationEntryStatus(@Param("applicationStatusId") Integer applicationStatusId,
                                      @Param("applicationId") Long applicationId,
                                      @Param("applicantId") Long applicantId,
                                      @Param("unumIsvalid") Integer unumIsvalid);
}
