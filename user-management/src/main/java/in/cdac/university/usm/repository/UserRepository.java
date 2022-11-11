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

    List<UmmtUserMst> findAllByGnumUseridInAndGnumIsvalidIn(List<Long> userIds, List<Integer> isValid);

    Optional<UmmtUserMst> findByGstrUserNameIgnoreCase(String username);

    @Modifying
    @Query("update UmmtUserMst a set a.gnumIsvalid = 0, " +
            "a.gdtLstmodDate = now(), " +
            "a.gnumLstmodBy = ?1 " +
            "where a.gnumUserid in (?2) and a.gnumIsvalid in (1, 2) ")
    Integer deleteUsers(Long modifyByUserId, List<Long> userIds);

    @Modifying(clearAutomatically = true)
    @Query("update UmmtUserMst u set u.gnumIsvalid = (select coalesce(max(a.gnumIsvalid), 2) + 1 " +
            "from UmmtUserMst a where a.gnumUserid = u.gnumUserid and a.gnumIsvalid > 2) " +
            "where u.gnumUserid in (?1) and u.gnumIsvalid in (1, 2) ")
    Integer createLog(Long userId);

    Optional<UmmtUserMst> findByGstrUserNameIgnoreCaseAndGnumUseridNot(String username, Long userId);

    @Query("select coalesce(max(a.gnumIsvalid), 2) + 1 from UmmtUserMst a where a.gnumUserid = ?1 and a.gnumIsvalid > 2")
    Integer getIsValidLogId(Long userId);

    @Query("select max(a.gnumUserid) + 1 from UmmtUserMst a")
    Long generateUserId();

    List<UmmtUserMst> findByGnumUserCatIdAndGnumIsvalid(Integer gnumUserCatId, Integer gnumIsvalid);


}
