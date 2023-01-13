package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationChklstDataDtl;
import in.cdac.university.globalService.entity.GbltConfigApplicationChklstDataDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListRepository extends JpaRepository<GbltConfigApplicationChklstDataDtl, GbltConfigApplicationChklstDataDtlPK> {
    List<GbltConfigApplicationChklstDataDtl> findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(Long unumApplicationId, Integer unumIsvalid, Integer unumUnivId);
}
