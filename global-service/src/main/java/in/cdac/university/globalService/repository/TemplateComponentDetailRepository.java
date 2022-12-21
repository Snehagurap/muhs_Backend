package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtlPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateComponentDetailRepository
		extends JpaRepository<GmstConfigTemplateComponentDtl, GmstConfigTemplateComponentDtlPK> {

	@Modifying
	@Query(value = "update university.gmst_config_template_component_dtl a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_component_dtl b "
			+ " where a.unum_templ_comp_id = b.unum_templ_comp_id and unum_isvalid > 2), udt_eff_to = current_timestamp where"
			+ " unum_templ_comp_id = :unumTemplCompId and unum_isvalid = 1", nativeQuery = true)
	Integer updateTemplateComponentItemRecord(@Param("unumTemplCompId") Long unumTemplCompId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update university.gmst_config_template_component_dtl a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_component_dtl b "
			+ " where a.unum_templ_comp_id = b.unum_templ_comp_id and unum_isvalid > 2), udt_eff_to = current_timestamp where"
			+ " unum_templ_comp_id in (:unumTemplCompId) and unum_isvalid = 1", nativeQuery = true)
	Integer deleteTemplateComponentItemRecord(@Param("unumTemplCompId") List<Long> idsToDelete);

	List<GmstConfigTemplateComponentDtl> findByUnumTempleCompIdInAndUnumIsvalidIn(List<Long> idsToDelete,
			List<Integer> unumIsvalids);

	List<GmstConfigTemplateComponentDtl> findByUnumTempleCompIdAndUnumIsvalid(Long unumTemplCompId, Integer unumIsvalid);
	
	
	@Query("select c from GmstConfigTemplateComponentDtl c " + "where c.unumIsvalid = 1 "
			+ "and c.unumUnivId = :universityId "
			+ "and c.unumTempleCompItemId in (select t.unumTemplCompItemId from GmstConfigTemplateDtl t "
			+ "where t.unumIsvalid = 1 " + "and t.unumUnivId = :universityId "
			+ "and t.unumTempleId in (:templateId) ) ")
	List<GmstConfigTemplateComponentDtl> findComponentDtlsByTemplateId(@Param("universityId") Integer universityId,
			@Param("templateId") List<Long> templateId);

}
