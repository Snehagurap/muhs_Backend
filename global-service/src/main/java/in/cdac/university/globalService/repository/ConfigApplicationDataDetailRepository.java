package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationDataDtl;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigApplicationDataDetailRepository extends JpaRepository<GbltConfigApplicationDataDtl, GbltConfigApplicationDataDtlPK> {
    @Modifying(clearAutomatically = true)
    @Query("delete from GbltConfigApplicationDataDtl " +
            "where UnumIsvalid = :isValid " +
            "and UnumUnivId = :universityId " +
            "and UnumApplicationId = :applicationId")
    int delete(@Param("isValid") Integer unumIsvalid, @Param("universityId") Integer unumUnivId, @Param("applicationId") Long unumApplicationId);

}
