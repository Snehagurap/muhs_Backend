package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateComponentDetailRepository extends JpaRepository<GmstConfigTemplateComponentDtl, GmstConfigTemplateComponentDtlPK> {
}
