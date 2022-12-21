package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateSubHeaderRepository extends JpaRepository<GmstConfigTemplateSubheaderMst, GmstConfigTemplateSubheaderMstPK> {

    @Query(value= "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_template_subheader_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GmstConfigTemplateSubheaderMst> findByUnumIsvalidAndUnumUnivIdAndUnumTempleHeadIdOrderByUnumSubheadDisplayOrderAsc(Integer unumIsvalid, Integer unumUnivId, Long unumTempleHeadId);

    Optional<GmstConfigTemplateSubheaderMst> findByUnumTempleSubheadIdAndUnumIsvalidAndUnumUnivId(Long unumTemplSubheadId, Integer unumIsvalid, Integer unumUnivId);

    List<GmstConfigTemplateSubheaderMst> findByUnumTempleSubheadIdInAndUnumIsvalidAndUnumUnivId(Collection<Long> unumTemplSubheadIds, Integer unumIsvalid, Integer unumUnivId);


    @Modifying(clearAutomatically = true)
    @Query("update GmstConfigTemplateSubheaderMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigTemplateSubheaderMst a where a.unumTempleSubheadId = u.unumTempleSubheadId and a.unumIsvalid > 2) " +
            "where u.unumTempleSubheadId in (:templSubheadId) and u.unumIsvalid in (1,2) " )
    Integer createLog(@Param("templSubheadId") List<Long> templSubheadId);
    
    @Query("select c from GmstConfigTemplateSubheaderMst c " + "where c.unumIsvalid = 1 "
			+ "and c.udtEffFrom <= current_date " + "and coalesce(c.udtEffTo, current_date) >= current_date "
			+ "and c.unumUnivId = :universityId and c.unumTempleHeadId = :unumTempleHeadId")
    List<GmstConfigTemplateSubheaderMst> findByunumTempleHeadId(Long unumTempleHeadId, Integer universityId);

	
	@Query("select c from GmstConfigTemplateSubheaderMst c " + "where c.unumIsvalid = 1 "
			+ "and c.unumUnivId = :universityId "
			+ "and c.unumTempleSubheadId in (select t.unumTempleSubheadId from GmstConfigTemplateDtl t "
			+ "where t.unumIsvalid = 1 " + "and t.unumUnivId = :universityId "
			+ "and t.unumTempleId in (:templateId) ) ")
	List<GmstConfigTemplateSubheaderMst> findSubHeadersByTemplateId(@Param("universityId") Integer universityId,
			@Param("templateId") List<Long> templateId);
}
