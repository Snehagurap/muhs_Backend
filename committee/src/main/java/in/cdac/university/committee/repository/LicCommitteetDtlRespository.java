package in.cdac.university.committee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeMstPK;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtlPK;

@Repository
public interface LicCommitteetDtlRespository extends JpaRepository<GbltLicCommitteeRuleSetDtl, GbltLicCommitteeRuleSetDtlPK> {


}
