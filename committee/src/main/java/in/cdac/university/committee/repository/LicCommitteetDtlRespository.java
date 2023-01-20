package in.cdac.university.committee.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeMemberDtlPK;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtlPK;

@Repository
public interface LicCommitteetDtlRespository extends JpaRepository<GbltLicCommitteeMemberDtl, GbltLicCommitteeMemberDtlPK> {


	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_lic_committee_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

	List<GbltLicCommitteeMemberDtl> findByUnumIsValidAndUnumUnivIdAndUnumLicId(int i, Integer universityId,
			Long unumLicId);
	
	@Modifying(clearAutomatically = true) 
    @Query("update GbltLicCommitteeMemberDtl u set u.unumIsValid = (select coalesce(max(a.unumIsValid), 2) + 1 " +
            "from GbltLicCommitteeMemberDtl a where a.unumLicId = u.unumLicId and a.unumIsValid > 2) " +
            "where u.unumLicId in (:unumLicId) and u.unumIsValid in (1, 2) ")
	Integer createLog(@Param("unumLicId") Long unumLicId);

	
	
}
