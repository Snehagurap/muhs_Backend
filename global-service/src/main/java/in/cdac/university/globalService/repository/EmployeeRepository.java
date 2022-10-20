package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.entity.GmstEmpMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<GmstEmpMst, GmstEmpMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_emp_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstEmpMst c " +
            "where c.unumIsvalid = :isValid " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrEmpName ")
    List<GmstEmpMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    List<GmstEmpMst> findByUnumIsvalidAndUnumUnivId(Integer unumIsvalid, Integer unumUnivId);



}
