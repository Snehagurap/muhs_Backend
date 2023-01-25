package in.cdac.university.committee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeMstPK;

@Repository
public interface LicCommitteetMstRespository extends JpaRepository<GbltLicCommitteeMst, GbltLicCommitteeMstPK> {

	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_lic_committee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
	
	List<GbltLicCommitteeMst> findByUnumIsValidInAndUstrLicNameIgnoreCaseAndUnumUnivId(List<Integer> of,
			String ustrLicName, Integer unumUnivId);

	List<GbltLicCommitteeMst> findByUnumIsValidAndUnumUnivId(int i, Integer universityId);

	GbltLicCommitteeMst findByUnumIsValidAndUnumUnivIdAndUnumLicId(int i, Integer universityId, Long unumLicId);
	
	@Modifying(clearAutomatically = true)
    @Query("update GbltLicCommitteeMst u set u.unumIsValid = (select coalesce(max(a.unumIsValid), 2) + 1 " +
            "from GbltLicCommitteeMst a where a.unumLicId = u.unumLicId and a.unumIsValid > 2) " +
            "where u.unumLicId in (:unumLicId) and u.unumIsValid in (1, 2) ")
	Integer createLog(@Param("unumLicId") Long unumLicId);

	List<GbltLicCommitteeMst> findByUnumIsValidInAndUstrLicNameIgnoreCaseNotAndUnumUnivId(List<Integer> of,
			String ustrLicName, Integer unumUnivId);


}
