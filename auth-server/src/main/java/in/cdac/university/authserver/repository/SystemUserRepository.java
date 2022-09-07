package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.UmstSystemUserMst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<UmstSystemUserMst, Integer> {

    Optional<UmstSystemUserMst> findByGstrSysLoginIdAndGblIsvalid(String username, Integer isValid);
}
