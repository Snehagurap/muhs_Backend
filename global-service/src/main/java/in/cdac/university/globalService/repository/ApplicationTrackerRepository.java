package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTrackerRepository extends JpaRepository<GbltConfigApplicationTracker, GbltConfigApplicationTrackerPK> {
}
