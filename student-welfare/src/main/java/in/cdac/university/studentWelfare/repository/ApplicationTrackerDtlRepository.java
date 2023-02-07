package in.cdac.university.studentWelfare.repository;

import in.cdac.university.studentWelfare.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.studentWelfare.entity.GbltConfigApplicationTrackerDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationTrackerDtlRepository extends JpaRepository<GbltConfigApplicationTrackerDtl, GbltConfigApplicationTrackerDtlPK> {
    @Query("select coalesce(max(unumApplicationStatusSno), 0) + 1 from GbltConfigApplicationTrackerDtl c  " +
            "where c.unumApplicationId = :applicantionId")
    Long getApplicationStatusSno(@Param("applicantionId") Long applicantionId);

    @Query("select c from GbltConfigApplicationTrackerDtl c " +
            "where unumApplicationStatusId = :applicationStatus " +
            "and unumApplicationId = :applicationId " +
            "and unumIsvalid = 1 ")
    Optional<GbltConfigApplicationTrackerDtl> getScrutinyDetails(@Param("applicationId") Long applicationId, @Param("applicationStatus") Integer applicationStatus);

    Optional<GbltConfigApplicationTrackerDtl> findByUnumApplicationIdAndUnumApplicationLevelIdAndUnumApplicationStatusSnoAndUnumIsvalidAndUnumUnivId(long unumApplicationId, Integer unumApplicationLevelId, long unumApplicationStatusSno, Integer unumIsvalid, Integer unumUnivId);

    Optional<GbltConfigApplicationTrackerDtl> findByUnumApplicationIdAndUnumApplicationLevelIdAndUnumIsvalidAndUnumUnivId(long unumApplicationId, Integer unumApplicationLevelId, Integer unumIsvalid, Integer unumUnivId);
	
	
	
	
	
	
}
