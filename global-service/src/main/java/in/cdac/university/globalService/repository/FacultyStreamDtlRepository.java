package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstFacultyStreamDtl;
import in.cdac.university.globalService.entity.GmstFacultyStreamDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacultyStreamDtlRepository extends JpaRepository<GmstFacultyStreamDtl, GmstFacultyStreamDtlPK> {


    //@Query("select max(a.unumFacStreamId) + 1 from GmstFacultyStreamDtl a")
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_faculty_stream_dtl')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    List<GmstFacultyStreamDtl> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);



    List<GmstFacultyStreamDtl> findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(Integer unumCfacultyId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstFacultyStreamDtl u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.unumUnivId = :univId and unumCfacultyId = :facultyId " +
            "and u.unumStreamId in (:streamIds)")
    Integer delete(@Param("facultyId") Integer facultyId ,
                   @Param("univId") Integer UnivId,
                   @Param("streamIds") List<Long> streamIds);




}
