package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeRulesetMst;
import in.cdac.university.committee.entity.GbltCommitteeRulesetMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommitteeRulesetMstRepository extends JpaRepository<GbltCommitteeRulesetMst, GbltCommitteeRulesetMstPK> {

    @Query(value = "select to_char(current_date,'yymm') || lpad(nextval('ucom.seq_gblt_committee_ruleset_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GbltCommitteeRulesetMst> findByUnumUnivIdAndUnumIsvalid(Integer unumUnivId, Integer unumIsvalid);

    Optional<GbltCommitteeRulesetMst> findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(Long unumComRsId, Integer unumIsvalids, Integer unumUnivId);

    List<GbltCommitteeRulesetMst> findByUstrComRsNameAndUnumIsvalidAndUnumUnivId(String ustrComRsName, Integer unumIsvalid, Integer unumUnivId);


    @Modifying(clearAutomatically = true)
    @Query("update GbltCommitteeRulesetMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GbltCommitteeRulesetMst a where a.unumComRsId = u.unumComRsId and a.unumIsvalid > 2) , " +
            "u.udtEffTo = current_date " +
            "where u.unumComRsId in (:unumComRsId) and u.unumIsvalid in (1,2) and u.unumUnivId = :universityId" )
    Integer createLog(@Param("unumComRsId") List<Long> unumComRsId, @Param("universityId") Integer universityId);
}
