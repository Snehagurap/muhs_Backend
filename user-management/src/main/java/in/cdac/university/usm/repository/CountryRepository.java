package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltCountryMstImsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<GbltCountryMstImsc, Integer> {

    List<GbltCountryMstImsc> findAllByGnumIsvalidOrderByGstrCountrynameAsc(Integer gnumIsvalid);

}
