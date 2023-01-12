package in.cdac.university.committee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMstPk;


@Repository
public interface LicCommitteeRuleSetMstRespository extends JpaRepository<GbltLicCommitteeRuleSetMst, GbltLicCommitteeRuleSetMstPk>{

}
