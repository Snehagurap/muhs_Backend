package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstConfigTemplateItemApiMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemApiMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateItemApiRepository extends JpaRepository<GmstConfigTemplateItemApiMst, GmstConfigTemplateItemApiMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('university.seq_gmst_config_template_item_api_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();

    @Query("select c from GmstConfigTemplateItemApiMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrom <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "order by c.unumApiId")
    List<GmstConfigTemplateItemApiMst> getAllTemplateItemApis();

    //for get by id
    Optional<GmstConfigTemplateItemApiMst> findByUnumIsvalidAndUnumApiId(Integer unumIsvalid, Long apiId);


    @Query("select a from GmstConfigTemplateItemApiMst a " +
            "where a.unumIsvalid = 1 " +
            "order by a.ustrApiName ")
    List<GmstConfigTemplateItemApiMst> listPageData();

    //for save
    List<GmstConfigTemplateItemApiMst> findByUnumIsvalidAndUstrApiNameIgnoreCase(Integer unumIsvalid, String ustrApiName);

    //for update
    @Modifying(clearAutomatically = true)
    @Query("update GmstConfigTemplateItemApiMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstConfigTemplateItemApiMst a where a.unumApiId = u.unumApiId and a.unumIsvalid > 2)," +
            "u.udtEffTo = now() " +
            "where u.unumApiId in (:apiId) and u.unumIsvalid = 1 ")
    Integer createLog(@Param("apiId") List<Long> apiId);

    List<GmstConfigTemplateItemApiMst> findByUnumIsvalidAndUstrApiNameIgnoreCaseAndUnumApiIdNot(Integer unumIsvalid, String ustrApiName, Long unumApiId);

    //for delete
    List<GmstConfigTemplateItemApiMst> findByUnumIsvalidAndUnumApiIdIn(Integer unumIsvalid, List<Long> idsToDelete);
}
