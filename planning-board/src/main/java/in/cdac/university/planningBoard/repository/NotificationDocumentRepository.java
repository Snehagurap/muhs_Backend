package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationDocDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDocDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDocumentRepository extends JpaRepository<GbltNotificationDocDtl, GbltNotificationDocDtlPK> {

}
