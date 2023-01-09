package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtDatasetMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface DatasetRepository extends JpaRepository<UmmtDatasetMst, Integer> {

    List<UmmtDatasetMst> findAllByGblIsvalidOrderByGstrDatasetNameAsc(Integer datasetId);

    Optional<UmmtDatasetMst> findByGstrDatasetNameIgnoreCaseAndGblIsvalidNot(String gstrDatasetName, Integer i);

    @Query("select max(a.gnumDatasetId) + 1 from UmmtDatasetMst a")
    Integer generateDataSetId();

    List<UmmtDatasetMst> findAllByGnumModuleIdAndGblIsvalidOrderByGstrDatasetNameAsc(Integer moduleId, Integer isValid);

    Optional<UmmtDatasetMst> findByGnumDatasetId(Integer datasetId);

    Optional<UmmtDatasetMst> findByGnumDatasetIdAndGblIsvalidNot(Integer datasetId, Integer isValid);

    Optional<UmmtDatasetMst> findByGstrDatasetNameIgnoreCaseAndGnumDatasetIdNotAndGblIsvalidNot(String datasetName, Integer datasetId, Integer isValid);

    List<UmmtDatasetMst> findAllByGnumDatasetIdInAndGblIsvalidNot(List<Integer> datasetId, Integer isValid);

    UmmtDatasetMst findAllByGnumDatasetIdAndGblIsvalid(Integer datasetId,Integer isValid);

    //  List<UmmtDatasetMst> findByGblIsvalidOrderByGstrRoleNameAsc(Integer isvalid);
}
