package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationMaster;
import in.cdac.university.planningBoard.entity.GbltNotificationMasterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface NotificationMasterRepository extends JpaRepository<GbltNotificationMaster, GbltNotificationMasterPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('upb.seq_gblt_notification_master')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GbltNotificationMaster> findByUnumIsvalidAndUstrAcademicYearOrderByUdtNDtDesc(Integer unumIsvalid, String ustrAcademicYear);

    @Modifying(clearAutomatically = true)
    @Query("update GbltNotificationMaster u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 1) + 1 " +
            "from GbltNotificationMaster a where a.unumNid = u.unumNid and a.unumIsvalid > 1) " +
            "where u.unumNid in (:notificationId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("notificationId") List<Long> notificationId);

    @Query("select c from GbltNotificationMaster c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId " +
            "and c.udtValidFrm <= current_date " +
            "and coalesce(c.udtValidTo, current_date) >= current_date " +
            "order by udtNDt desc ")
    List<GbltNotificationMaster> getActiveNotifications(@PathVariable("universityId") Integer universityId);

    @Modifying(clearAutomatically = true)
    @Query("select c from GbltNotificationDtl c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumNid in (select a.unumNid from GbltNotificationMaster a " +
            "where a.unumIsvalid = 1 " +
            "and a.ustrAcademicYear = :year " +
            "and a.unumStreamId = :streamId) ")
    List<GbltNotificationDtl> getNotificationComboByYear(@PathVariable("year") String year,@Param("streamId") Long streamId);
}
