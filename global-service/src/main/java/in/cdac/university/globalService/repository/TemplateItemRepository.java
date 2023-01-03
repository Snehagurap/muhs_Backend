package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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

    @Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.unumUnivId = :universityId " +
            "and c.unumTempleItemId in (select t.unumTempleItemId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = 1 " +
            "and t.unumUnivId = :universityId " +
            "and t.unumTempleId in (:templateId) ) ")
    List<GmstConfigTemplateItemMst> findItemsByTemplateId(@Param("universityId") Integer universityId, @Param("templateId") List<Long> templateId);

    List<GmstConfigTemplateItemMst> findByUnumTempleItemIdInAndUnumIsvalidInAndUnumUnivId(Collection<Long> unumTempleItemIds, Collection<Integer> unumIsvalids, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GmstConfigTemplateItemMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigTemplateItemMst a where a.unumTempleItemId = u.unumTempleItemId and  a.unumIsvalid > 2) " +
            "where u.unumTempleItemId in (:templItemId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("templItemId") List<Long> unumTempleItemId);

 
	List<GmstConfigTemplateItemMst> findByUnumTempleItemIdInAndUnumIsvalid(List<Long> unumTempleCompItemIdlist,Integer unumIsvalid);
	
	@Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = :unumIsvalid " +
            "and c.unumTempleItemId in (select t.unumTempleItemId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = :unumIsvalid " +
            "and t.unumTempleCompId in (:unumTempleCompItemIdlist) ) ")
	List<GmstConfigTemplateItemMst> findAllByUnumTempleItemIdInAndUnumIsvalid(@Param("unumTempleCompItemIdlist") List<Long> unumTempleCompItemIdlist,@Param("unumIsvalid") Integer unumIsvalid);
	
	@Query("select c from GmstConfigTemplateItemMst c " +
            "where c.unumIsvalid = :unumIsvalid " +
            "and c.unumTempleItemId in (select t.unumTempleItemId from GmstConfigTemplateDtl t " +
            "where t.unumIsvalid = :unumIsvalid " +
            "and t.unumTempleCompId = :unumTempleCompItemId ) order by c.unumItemDisplayOrder,c.unumTempleItemId")
	List<GmstConfigTemplateItemMst> findAllByUnumTempleItemIdAndUnumIsvalid(@Param("unumTempleCompItemId") Long unumTempleCompItemIdlist,@Param("unumIsvalid") Integer unumIsvalid);
}
