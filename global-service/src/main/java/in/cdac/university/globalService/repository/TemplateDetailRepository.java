package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateDetailRepository extends JpaRepository<GmstConfigTemplateDtl, GmstConfigTemplateDtlPK> {
}
