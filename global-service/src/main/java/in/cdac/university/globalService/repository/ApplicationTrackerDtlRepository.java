package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTrackerDtlRepository extends JpaRepository<GbltConfigApplicationTrackerDtl, GbltConfigApplicationTrackerDtlPK> {
    @Query("select coalesce(max(unumApplicationStatusSno), 0) + 1 from GbltConfigApplicationTrackerDtl c  where c.unumApplicationId = :applicantionId")
    Integer getApplicationStatusSno(@Param("applicantionId") Long applicantionId);
}
