package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationDataMst;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigApplicantDataMasterRepository extends JpaRepository<GbltConfigApplicationDataMst, GbltConfigApplicationDataMstPK> {

    @Query(value = "select to_char(current_date, 'yymm') || lpad(nextval('templedata.seq_gblt_config_application_data_mst')\\:\\:text, 6, '0')", nativeQuery = true)
    Long getNextId();
}
