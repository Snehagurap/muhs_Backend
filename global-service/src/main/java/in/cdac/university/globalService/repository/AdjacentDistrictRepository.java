package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstAdjacentDistrictDtl;
import in.cdac.university.globalService.entity.GmstAdjacentDistrictDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface AdjacentDistrictRepository extends JpaRepository<GmstAdjacentDistrictDtl, GmstAdjacentDistrictDtlPK> {

    //@Query(value = "select c from GmstAdjacentDistrictDtl c WHERE c.unumIsvalid =:isValid and c.numDistId =:distCode")
    List<GmstAdjacentDistrictDtl> findByNumDistIdAndUnumIsvalid(Integer distCode,Integer isValid);

    @Modifying(clearAutomatically = true)
    @Query("update GmstAdjacentDistrictDtl u set u.unumIsvalid = 0, u.udtEffTo = now() " +
            "where u.unumIsvalid = 1 and u.gnumStatecode = :stateCode and numDistId = :distCode " +
            "and u.numAdjacentDistId in (:adjdistrictIdsToDelete)")
    Integer delete(@Param("stateCode") Integer stateCode, @Param("distCode") Integer distCode, @Param("adjdistrictIdsToDelete") List<Integer> adjdistrictIdsToDelete);

     List<GmstAdjacentDistrictDtl> findByUnumIsvalidAndGnumStatecodeAndNumDistId(Integer isValid, Integer gnumStatecode, Integer numDistId);
}
