package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstNotificationRecpientMst;
import in.cdac.university.globalService.entity.GmstNotificationRecpientMstPK;
import in.cdac.university.globalService.entity.GmstNotificationTypeMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationReceiptRepository extends JpaRepository<GmstNotificationRecpientMst, GmstNotificationRecpientMstPK> {

    @Query("select n from GmstNotificationRecpientMst n " +
            "where n.unumIsvalid = 1 " +
            "and n.udtEffFrm <= current_date " +
            "and coalesce(n.udtEffTo, current_date) >= current_date " +
            "and n.unumUnivId = :universityId " +
            "order by ustrNrecFname ")
    List<GmstNotificationRecpientMst> getAllNotificationReceipts(@Param("universityId") int universityId);
}
