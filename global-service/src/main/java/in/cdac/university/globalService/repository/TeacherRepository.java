package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstTeacherMst;
import in.cdac.university.globalService.entity.GmstTeacherMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<GmstTeacherMst, GmstTeacherMstPK> {

    @Query("select c from GmstTeacherMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrTeacherName ")
    List<GmstTeacherMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);
}
