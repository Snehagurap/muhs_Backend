package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstDocumentMst;
import in.cdac.university.globalService.entity.GmstDocumentMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<GmstDocumentMst, GmstDocumentMstPK> {

    @Query("select p from GmstDocumentMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrom <= current_date " +
            "and coalesce(p.udtEffTo, current_date) >= current_date " +
            "order by unumDocId")
    List<GmstDocumentMst> getAllDocuments();
}
