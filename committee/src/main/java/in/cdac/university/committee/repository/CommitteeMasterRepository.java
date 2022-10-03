package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeMst;
import in.cdac.university.committee.entity.GbltCommitteeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommitteeMasterRepository extends JpaRepository<GbltCommitteeMst, GbltCommitteeMstPK> {


    @Query("select c from GbltCommitteeMst c " +
            "where c.unumIsvalid = 1 " +
            "and ((c.udtComStartDate <= :committeeStartDate and c.udtComEndDate >= :committeeStartDate) " +
            "or (c.udtComStartDate <= :committeeEndDate and c.udtComEndDate >= :committeeEndDate)) " +
            "and upper(c.ustrComName) = upper(:committeeName) " +
            "and c.unumUnivId = :universityId ")
    Optional<GbltCommitteeMst> duplicateCheck(@Param("committeeName") String committeeName,
                                              @Param("universityId") Integer universityId,
                                              @Param("committeeStartDate") Date committeeStartDate,
                                              @Param("committeeEndDate") Date committeeEndDate);

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_committee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query(value = "select max(unumIsvalid)" +
            "from GbltCommitteeMst " +
            "where unumComid = :committeeId ")
    Long getMaxIsValid(@Param("committeeId") Long committeeId);

    @Query("select c from GbltCommitteeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtComStartDate >= :start_date " +
            "and c.udtComEndDate >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrComName ")
    List<GbltCommitteeMst> activeCommitteeList(@Param("start_date") Date startDate, @Param("universityId") Integer universityId);

    List<GbltCommitteeMst> findByUnumUnivIdAndUnumIsvalidOrderByUstrComNameAsc(Integer unumUnivId, Integer unumIsvalid);

    Optional<GbltCommitteeMst> findByUnumIsvalidAndUnumComidAndUnumUnivId(Integer unumIsvalid, Long unumComid, Integer unumUnivId);


}
