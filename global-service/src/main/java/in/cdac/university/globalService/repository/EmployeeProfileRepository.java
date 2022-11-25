package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstEmpProfileDtl;
import in.cdac.university.globalService.entity.GmstEmpProfileDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<GmstEmpProfileDtl, GmstEmpProfileDtlPK> {

    List<GmstEmpProfileDtl> findByUnumIsvalidAndUnumUnivIdAndUnumFacultyIdIn(Integer unumIsvalid, Integer unumUnivId, List<Integer> unumFacultyId);

    @Query("select count(*) from GmstEmpProfileDtl " +
            "where unumEmpId= :empID")
    Long getMaxEmpProfileId(@Param("empID") Long empID);

}
