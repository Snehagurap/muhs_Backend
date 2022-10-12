package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateItemMstPK;
import in.cdac.university.globalService.entity.GmstConfigUicontrolTypeMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigUiControlTypeRepository extends JpaRepository<GmstConfigUicontrolTypeMst, GmstConfigTemplateItemMstPK> {

    @Query("select c from GmstConfigUicontrolTypeMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by ustrUictDesc ")
    List<GmstConfigUicontrolTypeMst> findAllControls(@Param("universityId") Integer universityId);
}
