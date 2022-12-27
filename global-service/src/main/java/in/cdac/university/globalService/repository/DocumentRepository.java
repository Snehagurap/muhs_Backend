package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstDocumentMst;
import in.cdac.university.globalService.entity.GmstDocumentMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<GmstDocumentMst, GmstDocumentMstPK> {


    @Query("select max(a.unumDocId) + 1 from GmstDocumentMst a")
    Integer getNextId();

    @Query("select p from GmstDocumentMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrom <= current_date " +
            "and coalesce(p.udtEffTo, current_date) >= current_date " +
            "order by unumDocId")
    List<GmstDocumentMst> getAllDocuments();

    @Query("select a from GmstDocumentMst a " +
            "where a.unumIsvalid = :isValid " +
            "order by a.ustrDocName ")
    List<GmstDocumentMst> listPageData(@Param("isValid") Integer isValid);

    Optional<GmstDocumentMst> findByUnumIsvalidInAndUnumDocId(Collection<Integer> unumIsvalids, Long unumDocId);

    List<GmstDocumentMst> findByUnumIsvalidInAndUstrDocNameIgnoreCase(Collection<Integer> unumIsvalids, String ustrDocName);

    //for update
    @Modifying(clearAutomatically = true)
    @Query("update GmstDocumentMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstDocumentMst a where a.unumDocId = u.unumDocId and a.unumIsvalid > 2) " +
            "where u.unumDocId in (:docId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("docId") List<Long> docId);

    List<GmstDocumentMst> findByUnumIsvalidInAndUstrDocCodeAndUstrDocNameIgnoreCaseAndUnumDocIdNot(Collection<Integer> unumIsvalids, String ustrDocCode, String ustrDocName, Long unumDocId);

    //for delete
    List<GmstDocumentMst> findByUnumIsvalidInAndUnumDocIdIn(Collection<Integer> unumIsvalids, List<Long> idsToDelete);
}
