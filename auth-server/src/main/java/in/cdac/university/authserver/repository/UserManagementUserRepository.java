package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.GmstUniversityMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserManagementUserRepository extends JpaRepository<GmstUniversityMst, Integer> {

    Optional<GmstUniversityMst> findByGstrUserNameAndUnumIsvalid(String gstrUserName, Integer unumIsvalid);
}
