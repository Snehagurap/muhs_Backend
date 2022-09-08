package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltDesignationMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<GbltDesignationMst, Integer> {

    List<GbltDesignationMst> findAllByGnumIsvalidOrderByGstrDesignationNameAsc(Integer gnumIsvalid);

}
