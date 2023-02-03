package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstSalutationMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalutationRepository extends JpaRepository<GmstSalutationMst, Integer> {
    @Query("select p from GmstSalutationMst p " +
            "where p.unumIsvalid = 1 " +
            "order by ustrSalutationFname")
    List<GmstSalutationMst> getAllSalutations();
}
