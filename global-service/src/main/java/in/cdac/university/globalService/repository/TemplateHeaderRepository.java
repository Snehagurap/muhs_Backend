package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMstPK;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateHeaderRepository extends JpaRepository<GmstConfigTemplateHeaderMst, GmstConfigTemplateHeaderMstPK> {

    @Query("select c from GmstConfigTemplateHeaderMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId " +
            "and c.unumTemplHeadId in (select t.unumTempleHeadId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = 1 " +
            "and t.unumUnivId = :universityId " +
            "and t.unumTempleId in (:templateId) ) ")
    List<GmstConfigTemplateHeaderMst> findHeadersByTemplateId(@Param("universityId") Integer universityId, @Param("templateId") List<Long> templateId);

}
