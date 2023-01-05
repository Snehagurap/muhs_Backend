package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<GmstConfigTemplateMst, GmstConfigTemplateMstPK> {
    List<GmstConfigTemplateMst> findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(Integer unumIsvalid, Integer unumUnivId, Collection<Long> unumTemplIds);

    @Modifying
	@Query(value = "update university.gmst_config_template_mst a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_mst b "
			+ " where a.unum_temple_id = b.unum_temple_id and unum_isvalid>2), udt_eff_to = current_timestamp where unum_temple_id = :unumTempleId and unum_isvalid = 1", nativeQuery = true)
	Integer updateTemplateMasterRecord(Long unumTempleId);
    
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_template_mst')\\:\\:text, 6, '0')", nativeQuery = true)
	Long getNextUnumTempleId();
    
    List<GmstConfigTemplateMst> findByUnumTempleIdInAndUnumIsvalidIn(List<Long> idsToDelete,List<Integer> unumIsvalids);
    
    @Modifying(clearAutomatically = true)
	@Query(value = "update university.gmst_config_template_mst a set "
			+ " unum_isvalid = (select coalesce(max(unum_isvalid), 2) + 1 from university.gmst_config_template_mst b "
			+ " where a.unum_temple_id = b.unum_temple_id and unum_isvalid > 2), udt_eff_to = current_timestamp where unum_temple_id in ( :idsToDelete ) and unum_isvalid = 1", nativeQuery = true)
	Integer deleteTemplateMstRecord(@Param("idsToDelete") List<Long> idsToDelete);
    
   GmstConfigTemplateMst findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(Integer unumIsvalid, Integer unumUnivId,  Long unumTemplId);
   List<GmstConfigTemplateMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);

   
   
    @Modifying(clearAutomatically = true)
	@Query(value = "update university.gmst_config_template_dtl a set "
			+ " unum_header_order_no = :headerDisplayOrder"
			+ " where unum_temple_head_id = :headerId and unum_isvalid =1 and unum_temple_id = :unumTempleId", nativeQuery = true)
    void updateHeaderOrder(@Param("unumTempleId") Long unumTempleId,
    		@Param("headerId") Integer headerId, @Param("headerDisplayOrder") Integer headerDisplayOrder);
    
    @Modifying(clearAutomatically = true)
	@Query(value = "update university.gmst_config_template_dtl a set "
			+ " unum_component_order_no = :componentDisplayOrder"
			+ " where unum_temple_head_id = :headerId and unum_isvalid =1 and unum_temple_id = :unumTempleId and unum_temple_comp_id = :unumTempleCompId", nativeQuery = true)
	void updateCompOrder(@Param("unumTempleId") Long unumTempleId, @Param("headerId") Integer headerId, 
			@Param("unumTempleCompId") Long unumTempleCompId, @Param("componentDisplayOrder") Integer componentDisplayOrder);
   

}
