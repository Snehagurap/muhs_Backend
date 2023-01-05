package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltScrutinycommitteeMst;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrutinycommitteeMstRepository extends JpaRepository<GbltScrutinycommitteeMst, GbltScrutinycommitteeMstPK> {
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_scrutinycommittee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
