package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<GmstConfigTemplateMst, GmstConfigTemplateMstPK> {
    List<GmstConfigTemplateMst> findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(Integer unumIsvalid, Integer unumUnivId, Collection<Long> unumTemplIds);
   GmstConfigTemplateMst findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(Integer unumIsvalid, Integer unumUnivId,  Long unumTemplId);
   List<GmstConfigTemplateMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);
}
