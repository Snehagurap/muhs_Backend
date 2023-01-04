package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeRulesetDtl;
import in.cdac.university.committee.entity.GbltCommitteeRulesetDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeRulesetDtlRepository extends JpaRepository<GbltCommitteeRulesetDtl, GbltCommitteeRulesetDtlPK> {


    List<GbltCommitteeRulesetDtl> findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(Long unumComRsId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltCommitteeRulesetDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GbltCommitteeRulesetDtl a where a.unumComRsId = u.unumComRsId and a.unumIsvalid > 2), " +
            "u.udtEffTo = current_date " +
            "where u.unumComRsId in (:unumComRsId) and u.unumIsvalid in (1,2) and u.unumUnivId = :universityId" )
    Integer createLog(@Param("unumComRsId") List<Long> unumComRsId, @Param("universityId") Integer universityId);
}
