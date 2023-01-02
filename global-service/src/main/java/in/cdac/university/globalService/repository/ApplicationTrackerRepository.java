package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerPK;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTrackerRepository extends JpaRepository<GbltConfigApplicationTracker, GbltConfigApplicationTrackerPK> {
	
	@Query("select c from GbltConfigApplicationTracker c " +
            "where unum_ndtl_id = :notificationDetailId " +
            "and unum_nid = :notificationId " +
            "and unumIsvalid = 1 ")
    Optional<GbltConfigApplicationTracker> getDepDetails(@Param("notificationId") Long notificationId,
	@Param("notificationDetailId") Long notificationDetailId);

}
