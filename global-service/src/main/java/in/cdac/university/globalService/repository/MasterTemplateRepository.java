package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigMastertemplateMst;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTemplateRepository extends JpaRepository<GmstConfigMastertemplateMst, GmstConfigMastertemplateMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.gmst_config_mastertemplate_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
