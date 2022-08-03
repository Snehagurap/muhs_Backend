package in.cdac.university.authserver.repository;

import in.cdac.university.authserver.entity.UmstSystemUserMst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<UmstSystemUserMst, Integer> {

    public UmstSystemUserMst findByGstrSysLoginIdAndGblIsvalid(String username, Integer isValid);
}
