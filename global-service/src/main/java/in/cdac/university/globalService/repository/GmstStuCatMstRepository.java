package in.cdac.university.globalService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStuCatMst;
import in.cdac.university.globalService.entity.GmstStuCatMstPK;

@Repository
public interface GmstStuCatMstRepository extends JpaRepository<GmstStuCatMst, GmstStuCatMstPK> {


	List<GmstStuCatMst> findByUnumIsvalidAndUnumUnivId(int i, Integer universityId);

}
