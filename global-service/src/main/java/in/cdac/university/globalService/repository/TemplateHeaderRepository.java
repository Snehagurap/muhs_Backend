package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateHeaderRepository extends JpaRepository<GmstConfigTemplateHeaderMst, GmstConfigTemplateHeaderMstPK> {

    List<GmstConfigTemplateHeaderMst> findByUnumIsvalidAndUnumUnivIdOrderByUnumHeadDisplayOrderAsc(Integer unumIsvalid, Integer unumUnivId);

}
