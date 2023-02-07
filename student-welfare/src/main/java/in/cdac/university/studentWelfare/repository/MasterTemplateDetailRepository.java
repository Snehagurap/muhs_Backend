package in.cdac.university.studentWelfare.repository;

import in.cdac.university.studentWelfare.entity.GmstConfigMastertemplateTemplatedtl;
import in.cdac.university.studentWelfare.entity.GmstConfigMastertemplateTemplatedtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTemplateDetailRepository extends JpaRepository<GmstConfigMastertemplateTemplatedtl, GmstConfigMastertemplateTemplatedtlPK> {
    List<GmstConfigMastertemplateTemplatedtl> findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(Integer unumIsvalid, Integer unumUnivId, Long unumMtempleId);

	List<GmstConfigMastertemplateTemplatedtl> findByUnumIsvalidInAndUnumMtempleId(List<Integer> of, Long unumMtempleId);
	
	@Modifying(clearAutomatically = true)
    @Query("update GmstConfigMastertemplateTemplatedtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigMastertemplateTemplatedtl a where a.unumMtempleId = u.unumMtempleId and a.unumIsvalid > 2) " +
            "where u.unumMtempleId in (:masterTempIds) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("masterTempIds") List<Long> masterTempIds);
}
