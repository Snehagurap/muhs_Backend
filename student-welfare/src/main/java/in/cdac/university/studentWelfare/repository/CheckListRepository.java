package in.cdac.university.studentWelfare.repository;

import in.cdac.university.studentWelfare.entity.GbltConfigApplicationChklstDataDtl;
import in.cdac.university.studentWelfare.entity.GbltConfigApplicationChklstDataDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListRepository extends JpaRepository<GbltConfigApplicationChklstDataDtl, GbltConfigApplicationChklstDataDtlPK> {
    List<GbltConfigApplicationChklstDataDtl> findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(Long unumApplicationId, Integer unumIsvalid, Integer unumUnivId);

    @Modifying(clearAutomatically = true)
    @Query("update GbltConfigApplicationChklstDataDtl u set u.unumOfcScrutinyIsitemverified = :ofcScrutinyIsitemverified , " +
            "u.ustrOfcScrutinyRemarks = :ofcScrutinyRemarks  " +
            "where u.unumApplicationId = :applicationId " +
            "and u.unumTempleItemId = :templeItemId ")
    Integer updateDepartmentVerification(@Param("applicationId") Long applicationId, @Param("templeItemId") Long templeItemId,
                                         @Param("ofcScrutinyIsitemverified") Integer ofcScrutinyIsitemverified,
                                         @Param("ofcScrutinyRemarks") String ofcScrutinyRemarks);
}
