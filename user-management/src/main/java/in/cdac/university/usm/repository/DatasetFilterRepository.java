package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtDatasetFilterMst;
import in.cdac.university.usm.entity.UmmtDatasetFilterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DatasetFilterRepository extends JpaRepository<UmmtDatasetFilterMst, UmmtDatasetFilterPK> {


    List<UmmtDatasetFilterMst> findByGnumDatasetIdAndGblIsvalid(Integer datasetId, Integer isValid);

    @Modifying(clearAutomatically = true)
    @Query(value = "delete from usm.ummt_dataset_filter_mst where gnum_dataset_id = :gnumDatasetId", nativeQuery = true)
    void deleteByGnumDatasetId(@Param("gnumDatasetId")Integer gnumDatasetId);


}
