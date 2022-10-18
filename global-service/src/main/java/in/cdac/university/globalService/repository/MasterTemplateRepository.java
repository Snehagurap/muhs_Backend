package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigMastertemplateMst;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTemplateRepository extends JpaRepository<GmstConfigMastertemplateMst, GmstConfigMastertemplateMstPK> {
}
