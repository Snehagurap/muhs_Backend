package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMstPK;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateComponentRepository
		extends JpaRepository<GmstConfigTemplateComponentMst, GmstConfigTemplateComponentMstPK> {

	@Query("select c from GmstConfigTemplateComponentMst c " + "where c.unumIsvalid = 1 "
			+ "and c.unumUnivId = :universityId "
			+ "and c.unumTemplCompId in (select t.unumTempleCompId from GmstConfigTemplateDtl t "
			+ "where t.unumIsvalid = 1 " + "and t.unumUnivId = :universityId "
			+ "and t.unumTempleId in (:templateId) ) ")
	List<GmstConfigTemplateComponentMst> findComponentsByTemplateId(@Param("universityId") Integer universityId,
			@Param("templateId") List<Long> templateId);

	List<GmstConfigTemplateComponentMst> findByUnumIsvalidAndUnumUnivIdOrderByUnumCompDisplayOrderAsc(
			Integer unumIsvalid, Integer unumUnivId);

	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_template_component_mst')\\:\\:text, 6, '0')", nativeQuery = true)
	Long getNextUnumTemplCompId();

	@Modifying
	@Query(value="update university.gmst_config_template_component_mst a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_component_mst b "
			+ " where a.unum_templ_comp_id = b.unum_templ_comp_id and unum_isvalid>2), udt_eff_to = current_timestamp where unum_templ_comp_id = :unumTemplCompId and unum_isvalid = 1", nativeQuery = true)
	Integer updateTemplateComponentRecord(@Param("unumTemplCompId") Long unumTemplCompId);

	@Modifying(clearAutomatically = true)
	@Query(value="update university.gmst_config_template_component_mst a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_component_mst b "
			+ " where a.unum_templ_comp_id = b.unum_templ_comp_id and unum_isvalid > 2), udt_eff_to = current_timestamp where unum_templ_comp_id in ( :unumTemplCompId ) and unum_isvalid = 1", nativeQuery = true)
	Integer deleteTemplateComponentRecord(@Param("unumTemplCompId") List<Long> idsToDelete);

	List<GmstConfigTemplateComponentMst> findByUnumTemplCompIdInAndUnumIsvalidIn(List<Long> idsToDelete,
			List<Integer> unumIsvalids);
	
}
