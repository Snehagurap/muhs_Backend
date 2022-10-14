package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationMaster;
import in.cdac.university.planningBoard.entity.GbltNotificationMasterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMasterRepository extends JpaRepository<GbltNotificationMaster, GbltNotificationMasterPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('upb.seq_gblt_notification_master')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GbltNotificationMaster> findByUnumIsvalidAndUstrAcademicYearOrderByUdtNDtDesc(Integer unumIsvalid, String ustrAcademicYear);


}
