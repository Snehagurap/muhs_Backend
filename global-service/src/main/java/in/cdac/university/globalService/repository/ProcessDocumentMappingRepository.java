package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstProcDocDtl;
import in.cdac.university.globalService.entity.GmstProcDocDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcessDocumentMappingRepository extends JpaRepository<GmstProcDocDtl, GmstProcDocDtlPK> {

    List<GmstProcDocDtl> findByUnumProcessIdAndUnumIsvalid(Long processId, Integer unumIsvalid);

    @Modifying(clearAutomatically = true)
    @Query("update GmstProcDocDtl u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.unumProcessId = :processId " +
            "and u.unumDocId in (:documentIds)")
    Integer delete(@Param("processId") Long processId, @Param("documentIds") List<Long> documentIds);
    List<GmstProcDocDtl> findByUnumIsvalid(Integer unumIsvalid);

    @Modifying(clearAutomatically = true)
    @Query("update GmstProcDocDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstProcDocDtl a where a.unumProcessId = u.unumProcessId and a.unumIsvalid > 1) " +
            "where u.unumProcessId = :processId and u.unumDocId in (:docIds) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("processId") Long processId , List<Long> docIds);
}
