package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateDetailRepository extends JpaRepository<GmstConfigTemplateDtl, GmstConfigTemplateDtlPK> {
    List<GmstConfigTemplateDtl> findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(Integer unumIsvalid, Integer unumUnivId, List<Long> unumTempleId);

	List<GmstConfigTemplateDtl> findByUnumTempleCompIdInAndUnumIsvalid(List<Long> unumTemplCompIdlist,Integer unumIsvalid);

	@Modifying
	@Query(value = "update university.gmst_config_template_dtl a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_dtl b "
			+ " where a.unum_temple_id = b.unum_temple_id and unum_isvalid > 2), udt_eff_to = current_timestamp where"
			+ " unum_temple_id = :unumTempleId and unum_isvalid = 1", nativeQuery = true)	
	Integer updateTemplateMasterDtlsRecord(Long unumTempleId);

	List<GmstConfigTemplateDtl> findByUnumTempleIdInAndUnumIsvalidIn(List<Long> idsToDelete, List<Integer> unumIsvalids);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update university.gmst_config_template_dtl a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_dtl b "
			+ " where a.unum_temple_id = b.unum_temple_id and unum_isvalid > 2), udt_eff_to = current_timestamp where"
			+ " unum_temple_id in (:idsToDelete) and unum_isvalid = 1", nativeQuery = true)
	Integer deleteTemplateDtlRecord(List<Long> idsToDelete);

}
