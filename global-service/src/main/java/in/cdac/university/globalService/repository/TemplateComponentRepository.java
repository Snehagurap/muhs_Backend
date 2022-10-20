package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMstPK;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateComponentRepository extends JpaRepository<GmstConfigTemplateComponentMst, GmstConfigTemplateComponentMstPK> {

    @Query("select c from GmstConfigTemplateComponentMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId " +
            "and c.unumTemplCompId in (select t.unumTempleCompId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = 1 " +
            "and t.unumUnivId = :universityId " +
            "and t.unumTempleId in (:templateId) ) ")
    List<GmstConfigTemplateComponentMst> findComponentsByTemplateId(@Param("universityId") Integer universityId, @Param("templateId") List<Long> templateId);
}
