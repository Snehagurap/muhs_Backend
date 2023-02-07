package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltStateMstImsc;
import in.cdac.university.usm.entity.GbltStateMstImscPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<GbltStateMstImsc, GbltStateMstImscPK> {

    @Query("select (coalesce(max(a.gnumStatecode),0) + 1) from GbltStateMstImsc a")
    Integer getNextId();

    @Modifying
    @Query(value = "Select c from GbltStateMstImsc c where  c.gnumStatecode = 64 and c.gnumIsvalid = 1")
    List<GbltStateMstImsc> findByGnumStatecodeAndGnumIsvalidOrderByGstrStatename();

    List<GbltStateMstImsc> findAllByGnumCountrycodeAndGnumIsvalidOrderByGstrStatenameAsc(Integer countryCode,Integer isValid);

    //for save
    List<GbltStateMstImsc> findByGnumIsvalidInAndGstrStatenameIgnoreCase(Collection<Integer> gnumIsvalids, String gstrStatename);

    //for update
    List<GbltStateMstImsc> findByGnumIsvalidInAndGstrStatenameIgnoreCaseAndGnumStatecodeNot(Collection<Integer> gnumIsvalids, String gstrStatename, Integer gnumStatecode);

    @Modifying(clearAutomatically = true)
    @Query("update GbltStateMstImsc u set u.gnumIsvalid = (select coalesce(max(a.gnumIsvalid), 2) + 1 " +
            "from GbltStateMstImsc a where a.gnumStatecode = u.gnumStatecode and a.gnumIsvalid > 2) " +
            "where u.gnumStatecode in (:stateCode) and u.gnumIsvalid in (1, 2) ")
    Integer createLog(@Param("stateCode") List<Integer> stateCode);

    //for delete
    List<GbltStateMstImsc> findByGnumIsvalidInAndGnumStatecodeIn(Collection<Integer> gnumIsvalids, List<Integer> idsToDelete);

    //for get by state code id
    Optional<GbltStateMstImsc> findByGnumIsvalidInAndGnumStatecode(Collection<Integer> gnumIsvalids, Integer stateCode);

    @Query("select a from GbltStateMstImsc a " +
            "where a.gnumIsvalid = :status " +
            "and a.gnumCountrycode = :countryCode " +
            "order by a.gstrStatename ")
    List<GbltStateMstImsc> listPageData(@Param("countryCode") Integer countryCode, @Param("status") Integer status);
}
