package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstNotificationStyleMst;
import in.cdac.university.globalService.entity.GmstNotificationStyleMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationStyleRepository extends JpaRepository<GmstNotificationStyleMst, GmstNotificationStyleMstPK> {

    @Query("select n from GmstNotificationStyleMst n " +
            "where n.unumIsvalid = 1 " +
            "and n.udtEffFrom <= current_date " +
            "and coalesce(n.udtEffTo, current_date) >= current_date " +
            "order by ustrNstyleFname")
    List<GmstNotificationStyleMst> getAllNotificationStyle();
}
