package in.cdac.university.globalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstStreamMstPK;

@Repository
public interface StudentRepository extends JpaRepository<GmstStreamMst, GmstStreamMstPK> {

}
