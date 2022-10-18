package in.cdac.university.planningBoard.repository;

import in.cdac.university.planningBoard.entity.GbltNotificationDocDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDocDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDocumentRepository extends JpaRepository<GbltNotificationDocDtl, GbltNotificationDocDtlPK> {
    List<GbltNotificationDocDtl> findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(Integer unumIsvalid, Integer unumUnivId, Long unumNid);

    @Modifying(clearAutomatically = true)
    @Query("update GbltNotificationDocDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 1) + 1 " +
            "from GbltNotificationDocDtl a where a.unumNid = u.unumNid and a.unumIsvalid > 1) " +
            "where u.unumNid in (:notificationId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("notificationId") List<Long> notificationId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltNotificationDocDtl u set u.unumIsvalid = 0, " +
            "unumEntryUid = :userId, udtEntryDate = now() " +
            "where u.unumNid in (:notificationId) and u.unumIsvalid = 1 ")
    Integer delete(@Param("notificationId") List<Long> notificationId, @Param("userId") Long userId);
}
