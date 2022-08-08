package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltStateMstImsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<GbltStateMstImsc, Integer> {

    List<GbltStateMstImsc> findAllByGnumIsvalidAndGnumCountrycodeOrderByGstrStatenameAsc(Integer gnumIsvalid, Integer countryCode);

}
