package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCollegeMst;
import in.cdac.university.globalService.entity.GmstCollegeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<GmstCollegeMst, GmstCollegeMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_college_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstCollegeMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by ustrColFname ")
    List<GmstCollegeMst> listPageData(@Param("isValid") Integer isValid, @Param("universityId") Integer universityId);

    @Query("select c from GmstCollegeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by ustrColFname ")
    List<GmstCollegeMst> findColleges(@Param("universityId") Integer universityId);

    Optional<GmstCollegeMst> findByUnumCollegeIdAndUnumIsvalidInAndUnumUnivId(Long unumCollegeId, Collection<Integer> unumIsvalids, Integer unumUnivId);

    List<GmstCollegeMst> findByUnumIsvalidInAndUstrColFnameIgnoreCaseAndUnumUnivId(Collection<Integer> unumIsvalids, String ustrColFname, Integer unumUnivId);

    List<GmstCollegeMst> findByUnumIsvalidInAndUstrColFnameIgnoreCaseAndUnumUnivIdAndUnumCollegeIdNot(Collection<Integer> unumIsvalids, String ustrColFname, Integer unumUnivId, Long collegeId);

    List<GmstCollegeMst> findByUnumCollegeIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumCollegeIds, Collection<Integer> unumIsvalids, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstCollegeMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstCollegeMst a where a.unumCollegeId = u.unumCollegeId and a.unumIsvalid > 2) " +
            "where u.unumCollegeId in (:collegeId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("collegeId") List<Long> collegeId);

    @Query("select count(*) from GmstCollegeMst a " +
            "where a.unumIsvalid = 1 ")
    Long totalNoOfColleges();
}
