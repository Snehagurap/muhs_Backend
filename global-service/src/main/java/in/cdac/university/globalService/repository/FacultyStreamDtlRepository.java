package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstFacultyStreamDtl;
import in.cdac.university.globalService.entity.GmstFacultyStreamDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyStreamDtlRepository extends JpaRepository<GmstFacultyStreamDtl, GmstFacultyStreamDtlPK> {

    List<GmstFacultyStreamDtl> findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(Integer unumCfacultyId, Integer unumIsvalid, Integer unumUnivId);
}
