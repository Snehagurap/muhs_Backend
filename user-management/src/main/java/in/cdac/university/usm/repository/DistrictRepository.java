package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltDistrictMstImsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<GbltDistrictMstImsc, Integer> {

    List<GbltDistrictMstImsc> findAllByGnumIsvalidAndGnumStatecodeOrderByStrDistNameAsc(Integer gnumIsvalid, Integer stateCode);

}
