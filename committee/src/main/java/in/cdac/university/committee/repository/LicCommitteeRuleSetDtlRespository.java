package in.cdac.university.committee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtlPK;

@Repository
public interface LicCommitteeRuleSetDtlRespository extends JpaRepository<GbltLicCommitteeRuleSetDtl, GbltLicCommitteeRuleSetDtlPK> {

	List<GbltLicCommitteeRuleSetDtl> findByUnumIsValidAndUnumUnivIdAndUnumComRsId(int i, Integer universityId,
			Long unumComRsId);
}
