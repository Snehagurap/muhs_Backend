package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigMastertemplateTemplatedtl;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateTemplatedtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTemplateDetailRepository extends JpaRepository<GmstConfigMastertemplateTemplatedtl, GmstConfigMastertemplateTemplatedtlPK> {
    List<GmstConfigMastertemplateTemplatedtl> findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(Integer unumIsvalid, Integer unumUnivId, Long unumMtempleId);


}
