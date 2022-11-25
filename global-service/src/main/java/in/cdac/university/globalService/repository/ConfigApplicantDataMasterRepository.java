package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationDataMst;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
