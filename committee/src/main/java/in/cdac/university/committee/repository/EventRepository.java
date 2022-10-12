package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltEventMst;
import in.cdac.university.committee.entity.GbltEventMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<GbltEventMst, GbltEventMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_event_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GbltEventMst c " +
            "where c.unumIsvalid = 1 " +
            "and ((c.udtEventFromdt <= :eventFromDate and c.udtEventTodt >= :eventFromDate) " +
            "or (c.udtEventFromdt <= :eventToDate and c.udtEventTodt >= :eventToDate)) " +
            "and upper(c.ustrEventName) = upper(:eventName) " +
            "and c.unumUnivId = :universityId ")
    Optional<GbltEventMst> duplicateCheck(@Param("universityId") Integer universityId,
                                          @Param("eventName") String ustrEventName,
                                          @Param("eventFromDate") Date eventFromDate,
                                          @Param("eventToDate") Date eventToDate);

    @Query("select c from GbltEventMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEventFromdt <= current_date " +
            "and c.udtEventTodt >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrEventName ")
    List<GbltEventMst> activeEventList(@Param("universityId") Integer universityId);

    @Query("select c from GbltEventMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEventTodt >= current_date " +
            "and c.unumUnivId = :universityId " +
            "and c.unumComid = :committeeId " +
            "order by c.ustrEventName ")
    List<GbltEventMst> activeEventListByCommitteeId(@Param("universityId") Integer universityId, @Param("committeeId") Long committeeId);

    @Query("select c from GbltEventMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumComid = :committeeId " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrEventName ")
    List<GbltEventMst> listPageData(@Param("universityId") Integer universityId, @Param("committeeId") Long committeeId);

    Optional<GbltEventMst> findByUnumEventidAndUnumUnivIdAndUnumIsvalid(Long unumEventid, Integer unumUnivId, Integer unumIsvalid);


}
