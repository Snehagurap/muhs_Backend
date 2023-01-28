package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltDistrictMstImsc;
import in.cdac.university.usm.entity.GbltDistrictMstImscPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<GbltDistrictMstImsc, GbltDistrictMstImscPK> {

    @Query("select (coalesce(max(a.numDistId),0) + 1) from GbltDistrictMstImsc a")
    Integer getNextId();

    List<GbltDistrictMstImsc> findAllByGnumIsvalidInAndGnumStatecodeOrderByStrDistNameAsc(Collection<Integer> gnumIsvalid, Integer stateCode);

    List<GbltDistrictMstImsc> findByGnumStatecodeAndGnumIsvalidInOrderByStrDistStName(Integer stateCode, Collection<Integer> isValid);

    //save
    List<GbltDistrictMstImsc> findByGnumIsvalidInAndGnumStatecodeAndStrDistNameIgnoreCase(Collection<Integer> gnumIsvalid, Integer gnumStatecode, String strDistName);

    List<GbltDistrictMstImsc> findByGnumIsvalidInAndGnumStatecodeAndStrDistNameIgnoreCaseAndNumDistIdNot(Collection<Integer> gnumIsvalid, Integer gnumStatecode, String strDistName, Integer numDistId);

    //for update
    @Modifying(clearAutomatically = true)
    @Query("update GbltDistrictMstImsc u set u.gnumIsvalid = (select coalesce(max(a.gnumIsvalid), 2) + 1 " +
            "from GbltDistrictMstImsc a where a.numDistId = u.numDistId and a.gnumIsvalid > 2)," +
            "u.gdtEffectiveTo = now() " +
            "where u.numDistId in (:districtId) and u.gnumIsvalid = 1 ")
    Integer createLog(@Param("districtId") List<Integer> districtId);

    //for delete
    List<GbltDistrictMstImsc> findByGnumIsvalidInAndNumDistIdIn(Collection<Integer> gnumIsvalid, List<Integer> idsToDelete);

    Optional<GbltDistrictMstImsc> findByGnumIsvalidInAndNumDistId(Collection<Integer> gnumIsvalid, Integer distCode);

    List<GbltDistrictMstImsc> findAllByGnumIsvalidAndGnumStatecodeOrderByStrDistNameAsc(Integer gnumIsvalid, Integer stateCode);
}
