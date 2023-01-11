package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigMastertemplateMst;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTemplateRepository extends JpaRepository<GmstConfigMastertemplateMst, GmstConfigMastertemplateMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_mastertemplate_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GmstConfigMastertemplateMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);

	List<GmstConfigMastertemplateMst> findByUnumIsvalidInAndUnumMtempleIdIn(List<Integer> of, List<Long> of2);
	
	List<GmstConfigMastertemplateMst> findByUnumIsvalidIn(List<Integer> of);

	List<GmstConfigMastertemplateMst> findByUnumIsvalidInAndUnumMtempleId(List<Integer> of, Long masterId);
	
	@Modifying(clearAutomatically = true)
    @Query("update GmstConfigMastertemplateMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigMastertemplateMst a where a.unumMtempleId = u.unumMtempleId and a.unumIsvalid > 2) " +
            "where u.unumMtempleId in (:masterTempIds) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("masterTempIds") List<Long> masterTempIds);
	
		List<GmstConfigMastertemplateMst> findByUnumIsvalidInAndUstrMtempleNameIgnoreCase(List<Integer> of,
			String ustrMtempleName);

}
