package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateSubHeaderRepository extends JpaRepository<GmstConfigTemplateSubheaderMst, GmstConfigTemplateSubheaderMstPK> {

    List<GmstConfigTemplateSubheaderMst> findByUnumIsvalidAndUnumUnivIdAndUnumTemplHeadIdOrderByUnumSubheadDisplayOrderAsc(Integer unumIsvalid, Integer unumUnivId, Long unumTemplHeadId);

}
