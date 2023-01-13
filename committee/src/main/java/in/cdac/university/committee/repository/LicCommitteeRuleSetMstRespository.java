package in.cdac.university.committee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMstPk;


@Repository
public interface LicCommitteeRuleSetMstRespository extends JpaRepository<GbltLicCommitteeRuleSetMst, GbltLicCommitteeRuleSetMstPk>{


	
	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_lic_committee_ruleset_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Integer getNextId();



	List<GbltLicCommitteeRuleSetMst> findByUnumIsValidInAndUstrComRsNameIgnoreCase(List<Integer> of,
			String ustrComRsName);

}
