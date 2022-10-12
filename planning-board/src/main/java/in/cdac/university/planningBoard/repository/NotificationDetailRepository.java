package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDetailRepository extends JpaRepository<GbltNotificationDtl, GbltNotificationDtlPK> {

}
