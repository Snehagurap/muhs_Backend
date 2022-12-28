package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstStreamMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamRepository extends JpaRepository<GmstStreamMst, GmstStreamMstPK> {

    List<GmstStreamMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);


}
