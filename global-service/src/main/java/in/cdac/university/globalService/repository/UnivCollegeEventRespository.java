package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.CmstUnivCollegeEventMst;
import in.cdac.university.globalService.entity.CmstUnivCollegeEventMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UnivCollegeEventRespository extends JpaRepository<CmstUnivCollegeEventMst, CmstUnivCollegeEventMstPK> {
    @Query("select c from CmstUnivCollegeEventMst c where " +
            "c.unumIsvalid = 1" +
            "and c.unumUnivId = :universityId " +
            "order by ustrColEventFname")
    List<CmstUnivCollegeEventMst> getAllCollegeEventCombo(Integer universityId);

}
