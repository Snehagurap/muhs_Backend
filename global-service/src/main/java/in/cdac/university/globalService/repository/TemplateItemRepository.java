package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateItemRepository extends JpaRepository<GmstConfigTemplateItemMst, GmstConfigTemplateItemMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_template_item_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId ")
    List<GmstConfigTemplateItemMst> getAllActiveItems(@Param("universityId") Integer universityId);

    @Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId ")
    List<GmstConfigTemplateItemMst> getAllItems(@Param("universityId") Integer universityId);

    @Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "and upper(concat(coalesce(ustrItemPrintPrefixText, ''), " +
            "                 coalesce(ustrItemPrintPreText, ''), " +
            "                 coalesce(ustrItemPrintPostText, ''))) = upper(:text)")
    List<GmstConfigTemplateItemMst> duplicate(@Param("universityId") Integer universityId, @Param("text") String text);
}
