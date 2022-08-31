package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtUserMst;
import in.cdac.university.usm.entity.UmmtUserMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UmmtUserMst, UmmtUserMstPK> {

    List<UmmtUserMst> findAllByUniversityMstUnumUnivIdAndGnumIsvalidOrderByGstrUserName(Integer universityId, Integer isValid);

    List<UmmtUserMst> findAllByGnumUseridInAndGnumIsvalidIn(List<Integer> userIds, List<Integer> isValid);

    Optional<UmmtUserMst> findByGstrUserNameIgnoreCaseAndGnumIsvalidIn(String username, List<Integer> isValid);

    @Modifying
    @Query("update UmmtUserMst a set a.gnumIsvalid = 0, " +
            "a.gdtLstmodDate = now(), " +
            "a.gnumLstmodBy = ?1 " +
            "where a.gnumUserid in (?2) and a.gnumIsvalid in (1, 2) ")
    Integer deleteUsers(Integer modifyByUserId, List<Integer> userIds);

    @Modifying(clearAutomatically = true)
    @Query("update UmmtUserMst u set u.gnumIsvalid = (select coalesce(max(a.gnumIsvalid), 2) + 1 " +
            "from UmmtUserMst a where a.gnumUserid = u.gnumUserid and a.gnumIsvalid > 2) " +
            "where u.gnumUserid in (?1) and u.gnumIsvalid in (1, 2) ")
    Integer createLog(Integer userId);

    Optional<UmmtUserMst> findByGstrUserNameIgnoreCaseAndGnumUseridNotAndGnumIsvalidIn(String username, Integer userId, List<Integer> isValid);

    @Query("select coalesce(max(a.gnumIsvalid), 2) + 1 from UmmtUserMst a where a.gnumUserid = ?1 and a.gnumIsvalid > 2")
    Integer getIsValidLogId(Integer userId);

    @Query("select max(a.gnumUserid) + 1 from UmmtUserMst a")
    Integer generateUserId();
}
