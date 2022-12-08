package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateDetailRepository extends JpaRepository<GmstConfigTemplateDtl, GmstConfigTemplateDtlPK> {
    List<GmstConfigTemplateDtl> findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(Integer unumIsvalid, Integer unumUnivId, List<Long> unumTempleId);

	List<Long> findAllUnumTempleItemIdByUnumTempleCompIdInAndUnumIsvalid(List<Long> unumTemplCompIdlist,Integer unumIsvalid);
	
	List<GmstConfigTemplateDtl> findAllByUnumTempleCompIdInAndUnumIsvalid(List<Long> unumTemplCompIdlist,Integer unumIsvalid);

}
