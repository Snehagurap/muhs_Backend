package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstRegionMst;
import in.cdac.university.globalService.entity.GmstRegionMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<GmstRegionMst, GmstRegionMstPK> {

    @Query("select g from GmstRegionMst g " +
            "where g.unumIsvalid = 1 " +
            "and g.udtEffFrm <= current_date " +
            "and coalesce(g.udtEffTo, current_date) >= current_date " +
            "and g.unumUnivId = :universityId " +
            "order by ustrRegionFname ")
    List<GmstRegionMst> getAllRegions(@Param("universityId") int universityId);
}
