package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtUserRoleMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UmmtUserRoleMst, Long> {

    List<UmmtUserRoleMst> findByGnumUserIdAndGblIsvalid(Integer gnumUserId, Integer gblIsvalid);

}
