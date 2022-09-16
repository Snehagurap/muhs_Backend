package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltEventMst;
import in.cdac.university.committee.entity.GbltEventMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<GbltEventMst, GbltEventMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_event_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
