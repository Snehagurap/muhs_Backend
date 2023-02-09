package in.cdac.university.studentWelfare.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplMst;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplPK;

@Repository
public interface GmstSwSavitbpSchemeApplMstRepository extends JpaRepository<GmstSwSavitbpSchemeApplMst, GmstSwSavitbpSchemeApplPK>{

	
    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('sw.seq_gmst_sw_savitbp_scheme_appl_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

	GmstSwSavitbpSchemeApplMst findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(int i,
			@NotNull(message = "Student ID is mandatory") Long unumStudentId, Integer universityId);


	List<GmstSwSavitbpSchemeApplMst> findByUnumIsvalidAndUnumUnivIdAndUnumSchemeId(int i, Integer universityId, long l);
}
