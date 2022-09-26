package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstSubjectMst;
import in.cdac.university.globalService.entity.GmstSubjectMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<GmstSubjectMst, GmstSubjectMstPK> {

    @Query("select s from GmstSubjectMst s " +
            "where s.unumIsvalid = :isValid " +
            "and s.unumUnivId = :universityId " +
            "order by s.ustrSubFname ")
    List<GmstSubjectMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);
}
