package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstProcessMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<GmstProcessMst, Integer> {

    @Query("select (coalesce(max(a.unumProcessId),0) + 1) from GmstProcessMst a")
    Integer getNextId();

    @Query("select p from GmstProcessMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrom <= current_date " +
            "and coalesce(p.udtEffTo, current_date) >= current_date " +
            "and p.unumUnivId = :universityId " +
            "order by ustrProcessName")
    List<GmstProcessMst> getAllProcess(@Param("universityId") Integer universityId);

    //for list Page
    @Query("select a from GmstProcessMst a " +
            "where a.unumIsvalid = :isValid " +
            "order by a.ustrProcessName ")
    List<GmstProcessMst> listPageData(@Param("isValid") Integer isValid);


    //for getProcessById
    Optional<GmstProcessMst> findByUnumIsvalidInAndUnumProcessId(Collection<Integer> unumIsvalids, Integer unumProcessId);

    //for save
    List<GmstProcessMst> findByUnumIsvalidInAndUstrProcessNameIgnoreCase(Collection<Integer> unumIsvalids, String ustrProcessName);

    //for update
    @Modifying(clearAutomatically = true)
    @Query("update GmstProcessMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstProcessMst a where a.unumProcessId = u.unumProcessId and a.unumIsvalid > 2) " +
            "where u.unumProcessId in (:processId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("processId") List<Integer> processId);

    List<GmstProcessMst> findByUnumIsvalidInAndUstrProcessNameIgnoreCaseAndUnumProcessIdNot(Collection<Integer> unumIsvalids, String ustrProcessName, Integer unumProcessId);

    //for delete
    List<GmstProcessMst> findByUnumIsvalidInAndUnumUnivIdAndUnumProcessIdIn(Collection<Integer> unumIsvalids, Integer unumUnivId, List<Integer> unumProcessId);

    List<GmstProcessMst> findByUnumIsvalid(Integer isValid);
}
