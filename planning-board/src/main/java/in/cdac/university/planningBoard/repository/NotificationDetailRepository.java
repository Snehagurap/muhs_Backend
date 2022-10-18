package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDetailRepository extends JpaRepository<GbltNotificationDtl, GbltNotificationDtlPK> {
    List<GbltNotificationDtl> findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(Integer unumIsvalid, Integer unumUnivId, Long unumNid);

    @Modifying(clearAutomatically = true)
    @Query("update GbltNotificationDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 1) + 1 " +
            "from GbltNotificationDtl a where a.unumNid = u.unumNid and a.unumIsvalid > 1) " +
            "where u.unumNid in (:notificationId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("notificationId") List<Long> notificationId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltNotificationDtl u set u.unumIsvalid = 0, " +
            "unumEntryUid = :userId, udtEntryDate = now() " +
            "where u.unumNid in (:notificationId) and u.unumIsvalid = 1 ")
    Integer delete(@Param("notificationId") List<Long> notificationId, @Param("userId") Long userId);
}
