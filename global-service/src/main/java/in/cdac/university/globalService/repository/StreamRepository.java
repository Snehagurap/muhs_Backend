package in.cdac.university.globalService.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstStreamMstPK;

@Repository
public interface StreamRepository extends JpaRepository<GmstStreamMst, GmstStreamMstPK> {

    List<GmstStreamMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);

    List<GmstStreamMst> findAllByunumIsvalid(int status);

    List<GmstStreamMst> findByUnumIsvalidInAndUnumStreamIdIn(List<Integer> isValid, List<Long> streamIds);


    @Query(value = "select (coalesce(max(a.unum_stream_id),0) + 1) from university.gmst_stream_mst a", nativeQuery = true)
    Long getNextId();

    @Modifying(clearAutomatically = true)
    @Query("update GmstStreamMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstStreamMst a where a.unumStreamId = u.unumStreamId and a.unumIsvalid > 2), " +
            "u.udtEffTo = now() " +
            "where u.unumStreamId in (:streamId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("streamId") List<Long> streamId);

    List<GmstStreamMst> findByUnumIsvalidInAndUstrStreamFnameIgnoreCase(List<Integer> of, String ustrStreamFname);

    List<GmstStreamMst> findByUnumIsvalidInAndUstrStreamFnameIgnoreCaseAndUnumStreamIdNot(List<Integer> of,
                                                                                          String ustrStreamFname, Long unumStreamId);

    Optional<GmstStreamMst>  findByUnumStreamIdAndUnumIsvalid(Long unumStreamId, Integer unumIsvalid);


}
