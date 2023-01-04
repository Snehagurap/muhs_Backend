package in.cdac.university.globalService.repository;


import in.cdac.university.globalService.entity.GmstPostMst;
import in.cdac.university.globalService.entity.GmstPostMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<GmstPostMst, GmstPostMstPK> {

    @Query("select max(a.unumPostId) + 1 from GmstPostMst a")
    Integer getNextId();

    @Query("select p from GmstPostMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrm <= current_date " +
            "and coalesce(p.udtEffTo, current_date) >= current_date " +
            "and p.unumUnivId = :universityId " +
            "order by ustrPostFname")
    List<GmstPostMst> getAllDesignations(@Param("universityId") Integer universityId);

    Optional<GmstPostMst> findByUnumIsvalidInAndUnumUnivIdAndUnumPostId(Collection<Integer> unumIsvalids, Integer unumUnivId, Integer unumPostId);

    //for listPage
    @Query("select a from GmstPostMst a " +
            "where a.unumIsvalid = :isValid " +
            "and a.unumUnivId = :universityId " +
            "order by a.ustrPostFname ")
    List<GmstPostMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    //for save
    List<GmstPostMst> findByUnumIsvalidInAndUnumUnivIdAndUstrPostFnameIgnoreCase(Collection<Integer> unumIsvalids, Integer unumUnivId, String ustrPostFname);

    //for update
    @Modifying(clearAutomatically = true)
    @Query("update GmstPostMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstPostMst a where a.unumPostId = u.unumPostId and a.unumIsvalid > 2)," +
            "u.udtEffTo = now() " +
            "where u.unumPostId in (:postId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("postId") List<Integer> postId);

    //for delete
    List<GmstPostMst> findByUnumIsvalidInAndUnumUnivIdAndUnumPostIdIn(Collection<Integer> unumIsvalids, Integer unumUnivId, List<Integer> unumPostId);

    List<GmstPostMst> findByUnumIsvalidInAndUnumUnivIdAndUstrPostFnameIgnoreCaseAndUnumPostIdNot(Collection<Integer> unumIsvalids, Integer unumUnivId, String ustrPostFname, Integer unumPostId);
}
