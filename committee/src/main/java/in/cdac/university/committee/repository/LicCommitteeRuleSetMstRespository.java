package in.cdac.university.committee.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMstPk;



@Repository
public interface LicCommitteeRuleSetMstRespository extends JpaRepository<GbltLicCommitteeRuleSetMst, GbltLicCommitteeRuleSetMstPk>{


	
	@Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_lic_committee_ruleset_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();


	List<GbltLicCommitteeRuleSetMst> findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumUnivId(List<Integer> of,
			String ustrComRsName, Integer universityId);


	List<GbltLicCommitteeRuleSetMst> findByUnumUnivIdAndUnumIsValid(Integer universityId, int i);

	List<GbltLicCommitteeRuleSetMst> getByUnumIsValidAndUnumUnivIdAndUnumComRsId(int i, Integer universityId,
			Long unumComRsId);
	
	@Modifying(clearAutomatically = true)
    @Query("update GbltLicCommitteeRuleSetMst u set u.unumIsValid = (select coalesce(max(a.unumIsValid), 2) + 1 " +
            "from GbltLicCommitteeRuleSetMst a where a.unumComRsId = u.unumComRsId and a.unumIsValid > 2) " +
            "where u.unumComRsId in (:unumComRsId) and u.unumIsValid in (1, 2) ")
	Integer createLog(@Param("unumComRsId") Long unumComRsId);


	List<GbltLicCommitteeRuleSetMst> findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumComRsIdNotAndUnumUnivId(
			List<Integer> of, String ustrComRsName,
			@NotNull(message = "Lic Committee Master ID is mandatory") Long unumComRsId, Integer universityId);

	List<GbltLicCommitteeRuleSetMst> findByUnumComRsIdAndUnumIsValidAndUnumUnivId(Long licCommitteeRsId, int i,
			Integer universityId);







}