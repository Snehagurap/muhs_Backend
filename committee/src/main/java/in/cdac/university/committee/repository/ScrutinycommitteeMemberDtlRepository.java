package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScrutinycommitteeMemberDtlRepository extends JpaRepository<GbltScrutinycommitteeMemberDtl, GbltScrutinycommitteeMemberDtlPK> {
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_scrutinycommittee_member_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GbltScrutinycommitteeMemberDtl> findByUnumScomIdAndUnumIsvalidAndUnumUnivId(Long unumScomId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltScrutinycommitteeMemberDtl u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid) , 2) + 1 " +
            "from GbltScrutinycommitteeMemberDtl a where a.unumScomId = u.unumScomId and a.unumIsvalid > 2 )" +
            "where u.unumScomId in (:scomIds) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("scomIds") List<Long> scomIds);
}
