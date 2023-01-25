package in.cdac.university.globalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStuSubcatMst;
import in.cdac.university.globalService.entity.GmstStuSubcatMstPK;

@Repository
public interface GmstStuSubcatMstRepository extends JpaRepository<GmstStuSubcatMst, GmstStuSubcatMstPK> {

}
