package in.cdac.university.committee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeMstPK;

@Repository
public interface LicCommitteetMstRespository extends JpaRepository<GbltLicCommitteeMst, GbltLicCommitteeMstPK> {

	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_lic_committee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
	
	List<GbltLicCommitteeMst> findByUnumIsValidInAndUstrLicNameIgnoreCase(List<Integer> of, String ustrLicName);

}
