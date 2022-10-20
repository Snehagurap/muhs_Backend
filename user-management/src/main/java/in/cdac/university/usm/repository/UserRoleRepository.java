package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtUserRoleMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UmmtUserRoleMst, Long> {

    List<UmmtUserRoleMst> findByGnumUserIdAndGblIsvalid(Integer gnumUserId, Integer gblIsvalid);

    @Query("select c from UmmtUserRoleMst c " +
            "where c.gnumUserId = :gnumUserId " +
            "and c.gblIsvalid = :gblIsvalid " +
            "and (c.gnumIsDefault = :gnumIsDefault or c.gnumRoleId = :gnumRoleId)")
    List<UmmtUserRoleMst> findMappedRoles(@Param("gnumUserId") Integer gnumUserId, @Param("gblIsvalid") Integer gblIsvalid, @Param("gnumIsDefault") Integer gnumIsDefault, @Param("gnumRoleId") Integer gnumRoleId);
}
