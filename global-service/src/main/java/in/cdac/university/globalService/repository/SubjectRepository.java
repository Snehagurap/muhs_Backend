package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstSubjectMst;
import in.cdac.university.globalService.entity.GmstSubjectMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<GmstSubjectMst, GmstSubjectMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_subject_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Modifying(clearAutomatically = true)
    @Query("update GmstSubjectMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstSubjectMst a where a.unumSubId = u.unumSubId and a.unumIsvalid > 2) " +
            "where u.unumSubId in (:subjectId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("subjectId") List<Long> subjectId);

    @Query("select s from GmstSubjectMst s " +
            "where s.unumIsvalid = :isValid " +
            "and s.unumUnivId = :universityId " +
            "order by s.ustrSubFname ")
    List<GmstSubjectMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    List<GmstSubjectMst> findByUnumIsvalidInAndUstrSubFnameIgnoreCase(List<Integer> unumIsvalid, String ustrSubFname);

    Optional<GmstSubjectMst> findByUnumSubIdAndUnumUnivIdAndUnumIsvalidIn(Long unumSubId, Integer unumUnivId, List<Integer> isValid);

    List<GmstSubjectMst> findByUnumIsvalidInAndUstrSubFnameIgnoreCaseAndUnumSubIdNot(List<Integer> unumIsvalid, String ustrSubFname, Long subjectId);

    List<GmstSubjectMst> findByUnumSubIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumSubIds, Collection<Integer> unumIsvalids, Integer unumUnivId);

    @Modifying
    @Query("update GmstSubjectMst a set a.unumIsvalid = 0, " +
            "udtEntryDate = current_timestamp, " +
            "unumEntryUid = :userId " +
            "where a.unumSubId in (:subjectIds) and a.unumIsvalid in (1, 2) ")
    Integer deleteSubjects(@Param("userId") Long userId, @Param("subjectIds") List<Long> subjectIdsToDelete);
}
