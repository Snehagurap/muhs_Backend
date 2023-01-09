package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtUserDatasetMst;
import in.cdac.university.usm.entity.UmmtUserDatasetPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserDatasetRepository extends JpaRepository<UmmtUserDatasetMst, UmmtUserDatasetPK> {

      @Query(value = "select c from UmmtUserDatasetMst c WHERE c.gnumIsvalid =:isValid and c.gnumUserId =:userId and c.gnumDatasetId =:datasetId ")
      List<UmmtUserDatasetMst> findByGnumUserIdAndGnumDatasetIdAndGnumIsvalidNot(@Param("userId") Long userId,
                                                                                  @Param("datasetId") Integer datasetId,
                                                                                  @Param("isValid") Integer isValid);

      List<UmmtUserDatasetMst> findByGnumUserIdAndGnumIsvalid(Long gnumUserId, Integer isValid);
}
