package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltDesignationMst;
import in.cdac.university.globalService.entity.GmstPostMst;
import in.cdac.university.globalService.entity.GmstPostMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<GmstPostMst, GmstPostMstPK> {

    @Query("select p from GmstPostMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrm <= current_date " +
            "and coalesce(p.udtEffTo, current_date) >= current_date " +
            "and p.unumUnivId = :universityId " +
            "order by ustrPostFname")
    List<GmstPostMst> getAllDesignations(@Param("universityId") Integer universityId);

}
