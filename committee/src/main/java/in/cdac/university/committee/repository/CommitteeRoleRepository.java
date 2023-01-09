package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GmstCommitteeRoleMst;
import in.cdac.university.committee.entity.GmstCommitteeRoleMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommitteeRoleRepository extends JpaRepository<GmstCommitteeRoleMst, GmstCommitteeRoleMstPK> {

    @Query("select colesce(max(a.unumRoleId),0) + 1 from GmstCommitteeRoleMst a")
    Integer getNextId();

    @Query("select c from GmstCommitteeRoleMst c " +
            "where c.unumIsvalid = 1 " +
            "and c.udtEffFrm <= current_date " +
            "and coalesce(c.udtEffTo, current_date) >= current_date " +
            "and c.unumUnivId = :universityId " +
            "order by c.ustrRoleFname")
    List<GmstCommitteeRoleMst> getAllCommitteeRoles(@Param("universityId") Integer universityId);

    Optional<GmstCommitteeRoleMst> findByUnumIsvalidInAndUnumUnivIdAndUnumRoleId(Collection<Integer> unumIsvalids, Integer unumUnivId, Integer unumRoleId);

    @Query("select a from GmstCommitteeRoleMst a " +
            "where a.unumIsvalid = :isValid " +
            "and a.unumUnivId = :universityId " +
            "order by a.ustrRoleFname ")
    List<GmstCommitteeRoleMst> listPageData(@Param("isValid") int isValid, @Param("universityId") int universityId);

    List<GmstCommitteeRoleMst> findByUnumIsvalidInAndUnumUnivIdAndUstrRoleFnameIgnoreCase(Collection<Integer> unumIsvalids, Integer unumUnivId, String ustrRoleFname);

    @Modifying(clearAutomatically = true)
    @Query("update GmstCommitteeRoleMst u set u.unumIsvalid = (select coalesce(max(a.unumIsvalid), 2) + 1 " +
            "from GmstCommitteeRoleMst a where a.unumRoleId = u.unumRoleId and a.unumIsvalid > 2) " +
            "where u.unumRoleId in (:roleId) and u.unumIsvalid in (1, 2) ")
    Integer createLog(@Param("roleId") List<Integer> roleId);

    List<GmstCommitteeRoleMst> findByUnumIsvalidInAndUstrRoleFnameIgnoreCaseAndUnumUnivIdAndUnumRoleIdNot(Collection<Integer> unumIsvalids, String ustrRoleFname, Integer unumUnivId, Integer unumRoleId);

    List<GmstCommitteeRoleMst> findByUnumIsvalidInAndUnumUnivIdAndUnumRoleIdIn(Collection<Integer> unumIsvalids, Integer unumUnivId, List<Integer> unumRoleId);
}
