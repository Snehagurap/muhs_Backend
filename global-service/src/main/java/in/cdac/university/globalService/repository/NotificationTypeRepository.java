package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstNotificationTypeMst;
import in.cdac.university.globalService.entity.GmstNotificationTypeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationTypeRepository extends JpaRepository<GmstNotificationTypeMst, GmstNotificationTypeMstPK> {

    @Query("select n from GmstNotificationTypeMst n " +
            "where n.unumIsvalid = 1 " +
            "and n.udtEffFrm <= current_date " +
            "and coalesce(n.udtEffTo, current_date) >= current_date " +
            "and n.unumUnivId = :universityId " +
            "order by ustrNtypeFname")
    List<GmstNotificationTypeMst> getAllValidNotificationTypes(@Param("universityId") int universityId);

    List<GmstNotificationTypeMst> findByUnumIsvalidInOrderByUstrNtypeFname(List<Integer> unumIsvalid);

}
