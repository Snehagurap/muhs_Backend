package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtDatasetMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetRepository extends JpaRepository<UmmtDatasetMst, Integer> {

    List<UmmtDatasetMst> findAllByGblIsvalidOrderByGstrDatasetName(Integer gnumIsvalid);

}
