package in.cdac.university.globalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStudentMst;
import in.cdac.university.globalService.entity.GmstStudentMstPK;


@Repository
public interface StudentRepository extends JpaRepository<GmstStudentMst, GmstStudentMstPK> {

}
