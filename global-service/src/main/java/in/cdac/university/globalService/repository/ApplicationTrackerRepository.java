package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationTrackerRepository extends JpaRepository<GbltConfigApplicationTracker, GbltConfigApplicationTrackerPK> {

    @Query("select c from GbltConfigApplicationTracker c " +
            "where unumIsvalid = 1 " +
			"and unumApplicationLevelId = :levelId " +
			"and unum_ndtl_id = :notificationDetailId " +
            "and unum_nid = :notificationId ")
	List<GbltConfigApplicationTracker> getApplicationForDepartmentScrutiny(@Param("notificationId") Long notificationId,
																		   @Param("notificationDetailId") Long notificationDetailId,
																		   @Param("levelId") Integer levelId);

}
