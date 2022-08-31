package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GmstUniversityMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<GmstUniversityMst, Integer> {

    List<GmstUniversityMst> findAllByUnumIsvalidOrderByUstrUnivFname(Integer isValid);

}
