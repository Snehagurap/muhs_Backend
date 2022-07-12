package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.UmmtUserMst;
import in.cdac.university.authserver.entity.UmmtUserMstId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UmmtUserMst, UmmtUserMstId> {

    @Query("select a from UmmtUserMst a where gnumIsvalid = ?1 and gstrUsername = ?2")
    UmmtUserMst findByUsername(Integer gnumIsvalid, String gstrUsername);
}
