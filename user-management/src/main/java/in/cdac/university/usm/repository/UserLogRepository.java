package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.GbltUserLog;
import in.cdac.university.usm.entity.GbltUserLogPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<GbltUserLog, GbltUserLogPK> {

    @Modifying
    @Query("update GbltUserLog set gdtLoguttDate = now() " +
            "where gdtLoguttDate is null " +
            "and gstrIpNumber = :ipAddress " +
            "and gnumUserid = :userId " +
            "and gnumLogId = :logId")
    int logoutUser(@Param("userId") Long userId, @Param("ipAddress") String ipAddress, @Param("logId") Long logId);
}
