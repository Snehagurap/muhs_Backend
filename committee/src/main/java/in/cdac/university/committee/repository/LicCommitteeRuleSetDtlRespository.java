package in.cdac.university.committee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtlPK;

@Repository
public interface LicCommitteeRuleSetDtlRespository extends JpaRepository<GbltLicCommitteeRuleSetDtl, GbltLicCommitteeRuleSetDtlPK> {

	List<GbltLicCommitteeRuleSetDtl> findByUnumIsValidAndUnumUnivIdAndUnumComRsId(int i, Integer universityId,
			Long unumComRsId);
	
	@Modifying(clearAutomatically = true)
    @Query("update GbltLicCommitteeRuleSetDtl u set u.unumIsValid = (select coalesce(max(a.unumIsValid), 2) + 1 " +
            "from GbltLicCommitteeRuleSetDtl a where a.unumComRsDtlId = u.unumComRsDtlId and a.unumIsValid > 2) " +
            "where u.unumComRsId in (:unumComRsId) and u.unumIsValid in (1, 2) ")
    Integer createLog(@Param("unumComRsId") Long unumComRsId);
}
