package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstProcDocDtl;
import in.cdac.university.globalService.entity.GmstProcDocDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentProcessRepository extends JpaRepository<GmstProcDocDtl, GmstProcDocDtlPK> {
    List<GmstProcDocDtl> findByUnumIsvalidAndUnumProcessIdOrderByUstrDocFnameAsc(Integer unumIsvalid, Long unumProcessId);
}
