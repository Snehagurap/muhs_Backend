package in.cdac.university.studentWelfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplMst;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplPK;

@Repository
public interface GmstSwSavitbpSchemeApplMstRepository extends JpaRepository<GmstSwSavitbpSchemeApplMst, GmstSwSavitbpSchemeApplPK>{

}
