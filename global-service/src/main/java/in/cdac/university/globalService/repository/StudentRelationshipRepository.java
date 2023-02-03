package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GmstStuRelationshipMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRelationshipRepository extends JpaRepository<GmstStuRelationshipMst, Integer> {

    @Query("select p from GmstStuRelationshipMst p " +
            "where p.unumIsvalid = 1 " +
            "and p.udtEffFrom <= current_date " +
            "and p.unumUnivId = :universityId ")
    List<GmstStuRelationshipMst> getAllStuRelations(@Param("universityId") Integer universityId);

}
