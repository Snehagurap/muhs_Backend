package in.cdac.university.globalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstStreamMstPK;

public interface StudentRepository extends JpaRepository<GmstStreamMst, GmstStreamMstPK> {

}
