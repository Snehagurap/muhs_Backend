package in.cdac.university.studentWelfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplTrackerdtlPK;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplTrackermst;

@Repository
public interface GmstSwSavitbpSchemeApplTrackermstRepository extends JpaRepository<GmstSwSavitbpSchemeApplTrackermst, GmstSwSavitbpSchemeApplTrackerdtlPK> {

}
