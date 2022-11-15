package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationDataDtl;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigApplicationDataDetailRepository extends JpaRepository<GbltConfigApplicationDataDtl, GbltConfigApplicationDataDtlPK> {

}
