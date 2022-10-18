package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateComponentRepository extends JpaRepository<GmstConfigTemplateComponentMst, GmstConfigTemplateComponentMstPK> {
}
