package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.entity.GmstEmpMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<GmstEmpMst, GmstEmpMstPK> {

    @Query("select c from GmstEmpMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrEmpName ")
    List<GmstEmpMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    List<GmstEmpMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);
}
