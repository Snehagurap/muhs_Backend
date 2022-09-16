package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GmstCommitteeRoleMst;
import in.cdac.university.committee.entity.GmstCommitteeRoleMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeRoleRepository extends JpaRepository<GmstCommitteeRoleMst, GmstCommitteeRoleMstPK> {

    @Query("select c from GmstCommitteeRoleMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrm <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrRoleFname")
    List<GmstCommitteeRoleMst> getAllCommitteeRoles(@Param("universityId") Integer universityId);
}
