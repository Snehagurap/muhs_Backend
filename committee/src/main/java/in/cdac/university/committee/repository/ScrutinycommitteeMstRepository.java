package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltScrutinycommitteeMst;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrutinycommitteeMstRepository extends JpaRepository<GbltScrutinycommitteeMst, GbltScrutinycommitteeMstPK> {
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('ucom.seq_gblt_scrutinycommittee_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    Optional<GbltScrutinycommitteeMst> findByUnumScomIdAndUnumIsvalidAndUnumUnivId(Long unumScomId, Integer unumIsvalid, Integer unumUnivId);

    List<GbltScrutinycommitteeMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);

    List<GbltScrutinycommitteeMst> findByUstrScomNameIgnoreCaseAndUnumScomCfacultyIdAndUnumIsvalidAndUnumUnivId(String committeeName, Long unumScomCfacultyId, Integer unumIsvalid, Integer unumUnivId);

    List<GbltScrutinycommitteeMst> findByUstrScomNameIgnoreCaseAndUnumScomCfacultyIdAndUnumIsvalidAndUnumUnivIdAndUnumScomIdNot(String committeeName, Long unumScomCfacultyId, Integer unumIsvalid, Integer unumUnivId, Long unumScomId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltScrutinycommitteeMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid) , 2) + 1 " +
            "from GbltScrutinycommitteeMst a where a.unumScomId = u.unumScomId and a.unumIsvalid > 2 ) " +
            "where u.unumScomId in (:scomIds) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("scomIds") List<Long> scomIds);

}
