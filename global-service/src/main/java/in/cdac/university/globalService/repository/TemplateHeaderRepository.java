package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMstPK;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateHeaderRepository extends JpaRepository<GmstConfigTemplateHeaderMst, GmstConfigTemplateHeaderMstPK> {

    @Query("select coalesce(max(unumTemplHeadId), 0) + 1 from GmstConfigTemplateHeaderMst")
    Long getNextId();

    @Query("select c from GmstConfigTemplateHeaderMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId " +
            "and c.unumTemplHeadId in (select t.unumTempleHeadId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = 1 " +
            "and t.unumUnivId = :universityId " +
            "and t.unumTempleId in (:templateId) ) ")
    List<GmstConfigTemplateHeaderMst> findHeadersByTemplateId(@Param("universityId") Integer universityId, @Param("templateId") List<Long> templateId);

    List<GmstConfigTemplateHeaderMst> findByUnumIsvalidAndUnumUnivIdOrderByUstrTemplHeadCodeAsc(Integer unumIsvalid, Integer unumUnivId);

    List<GmstConfigTemplateHeaderMst> findByUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(String ustrHeadPrintText, Integer unumIsvalid, Integer unumUnivId);

    Optional<GmstConfigTemplateHeaderMst> findByUnumTemplHeadIdAndUnumIsvalidAndUnumUnivId(Long unumTemplHeadId, Integer unumIsvalid, Integer unumUnivId);

    List<GmstConfigTemplateHeaderMst> findByUnumTemplHeadIdNotAndUstrHeadPrintTextAndUnumIsvalidAndUnumUnivId(Long unumTemplHeadId, String ustrHeadPrintText, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstConfigTemplateHeaderMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigTemplateHeaderMst a where a.unumTemplHeadId = u.unumTemplHeadId and a.unumIsvalid > 2) " +
            "where u.unumTemplHeadId in (:templHeadId) and u.unumIsvalid in (1,2) " )
    Integer createLog(List<Long> templHeadId);
}
