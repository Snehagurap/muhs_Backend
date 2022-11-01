package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltCommitteeMemberDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommitteeMemberMappingRepository extends JpaRepository<GbltCommitteeMemberDtl, GbltCommitteeMemberDtlPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_committee_member_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GbltCommitteeMemberDtl> findByUnumEventidAndUnumIsvalidAndUnumUnivId(Long unumEventid, Integer unumIsvalid, Integer unumUnivId);


}
