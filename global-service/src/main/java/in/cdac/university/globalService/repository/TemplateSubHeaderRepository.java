package in.cdac.university.globalService.repository;

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

    List<GmstConfigTemplateSubheaderMst> findByUnumIsvalidAndUnumUnivIdAndUnumTemplHeadIdOrderByUnumSubheadDisplayOrderAsc(Integer unumIsvalid, Integer unumUnivId, Long unumTemplHeadId);

    Optional<GmstConfigTemplateSubheaderMst> findByUnumTemplSubheadIdAndUnumIsvalidAndUnumUnivId(Long unumTemplSubheadId, Integer unumIsvalid, Integer unumUnivId);

    List<GmstConfigTemplateSubheaderMst> findByUnumTemplSubheadIdInAndUnumIsvalidAndUnumUnivId(Collection<Long> unumTemplSubheadIds, Integer unumIsvalid, Integer unumUnivId);


    @Modifying(clearAutomatically = true)
    @Query("update GmstConfigTemplateSubheaderMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigTemplateSubheaderMst a where a.unumTemplSubheadId = u.unumTemplSubheadId and a.unumIsvalid > 2) " +
            "where u.unumTemplSubheadId in (:templSubheadId) and u.unumIsvalid in (1,2) " )
    Integer createLog(@Param("templSubheadId") List<Long> templSubheadId);
}
