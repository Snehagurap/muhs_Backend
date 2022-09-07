package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.UmmtUserMst;
import in.cdac.university.authserver.entity.UmmtUserMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UmmtUserMst, UmmtUserMstPK> {

    @Query("select a from UmmtUserMst a where a.gstrUserName = ?1 and a.id.gnumIsvalid = ?2")
    Optional<UmmtUserMst> findUser(String gstrUsername, Integer gnumIsvalid);

}
