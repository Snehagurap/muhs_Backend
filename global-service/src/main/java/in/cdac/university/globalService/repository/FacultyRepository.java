package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.entity.GmstCoursefacultyMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<GmstCoursefacultyMst, GmstCoursefacultyMstPK> {

    @Query("select c from GmstCoursefacultyMst c where " +
            "c.unumIsvalid = 1" +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by ustrCfacultyFname")
    List<GmstCoursefacultyMst> getAllFaculty(@Param("universityId") Integer universityId);

    List<GmstCoursefacultyMst> findByUnumIsvalid(Integer unumIsvalid);

	Optional<GmstCoursefacultyMst> findByUnumCfacultyIdAndUnumIsvalid(Integer unumFacultyId, int unumIsvalid);


}
