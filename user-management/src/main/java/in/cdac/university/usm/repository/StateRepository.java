package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltStateMstImsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<GbltStateMstImsc, Integer> {
    @Modifying
    @Query(value = "Select c from GbltStateMstImsc c where  c.gnumStatecode = 64 and c.gnumIsvalid = 1")
    List<GbltStateMstImsc> findByGnumStatecodeAndGnumIsvalidOrderByGstrStatename();

    List<GbltStateMstImsc> findAllByGnumIsvalidAndGnumCountrycodeOrderByGstrStatenameAsc(Integer gnumIsvalid, Integer countryCode);

}
