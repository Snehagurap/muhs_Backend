package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeMst;
import in.cdac.university.committee.entity.GbltCommitteeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeMasterRepository extends JpaRepository<GbltCommitteeMst, GbltCommitteeMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_committee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query(value = "select max(unumIsvalid)" +
            "from GbltCommitteeMst " +
            "where unumComid = :committeeId ")
    Long getMaxIsValid(@Param("committeeId") Long committeeId);

    List<GbltCommitteeMst> findByUnumUnivIdAndUnumIsvalidOrderByUstrComNameAsc(Integer unumUnivId, Integer unumIsvalid);


}
