package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstDepartmentMst;
import in.cdac.university.globalService.entity.GmstDepartmentMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<GmstDepartmentMst, GmstDepartmentMstPK> {

    List<GmstDepartmentMst> findByUnumIsvalidOrderByUstrDeptFname(Integer isValid);
}
