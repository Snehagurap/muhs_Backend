package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.entity.GmstApplicantMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<GmstApplicantMst, GmstApplicantMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_applicant_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
