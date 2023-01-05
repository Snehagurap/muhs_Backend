package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScrutinycommitteeMemberDtlRepository extends JpaRepository<GbltScrutinycommitteeMemberDtl, GbltScrutinycommitteeMemberDtlPK> {
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_scrutinycommittee_member_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
