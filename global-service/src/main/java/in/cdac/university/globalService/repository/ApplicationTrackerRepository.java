package in.cdac.university.globalService.repository;

import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationTrackerRepository extends JpaRepository<GbltConfigApplicationTracker, GbltConfigApplicationTrackerPK> {

    @Query("select c from GbltConfigApplicationTracker c " +
            "where unumIsvalid = 1 " +
			"and unumApplicationLevelId >= (:minLevel) " +
			"and unumApplicationLevelId in (:levelIds) " +
			"and concat(unum_nid, ',', unum_ndtl_id) in (:notificationId) ")
	List<GbltConfigApplicationTracker> getApplicationForDepartmentScrutiny(@Param("notificationId") List<String> notificationId,
																		   @Param("levelIds") List<Integer> levelIds,
																		   @Param("minLevel") Integer minLevel);

	@Query("select c from GbltConfigApplicationTracker c " +
			"where unumIsvalid = 1 " +
			"and unumApplicationLevelId >= (:minLevel) " +
			"and unumApplicationLevelId not in (:levelIds) " +
			"and concat(unum_nid, ',', unum_ndtl_id) in (:notificationId) ")
	List<GbltConfigApplicationTracker> getProcessedApplication(@Param("notificationId") List<String> notificationId,
															   @Param("levelIds") List<Integer> levelIds,
															   @Param("minLevel") Integer minLevel);

	Optional<GbltConfigApplicationTracker> findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(Long unumApplicationId, Integer unumIsvalid, Integer unumUnivId);


	@Modifying(clearAutomatically = true)
	@Query("update GbltConfigApplicationTracker u set u.unumApplicationLevelId = :levelId ,  " +
			"u.unumApplicationStatusSno = :applicationStatusSno " +
			"where u.unumApplicationId = :applicationId ")
	Integer updateApplicationLevelAndStatusSNo(@Param("applicationId")Long applicationId,@Param("levelId") Integer levelId,
											   @Param("applicationStatusSno") Long applicationStatusSno);

	@Modifying(clearAutomatically = true)
	@Query("update GbltConfigApplicationTracker u set u.unumApplicationLevelId = :levelId ,  " +
			"u.unumApplicationStatusSno = :applicationStatusSno ,  " +
			"u.unumApplicationStatusId = :applicationStatusId , " +
			"u.unumDecisionStatusId = :decisionStatusId , " +
			"u.udtApplicationStatusDt = :applicationStatusDate " +
			"where u.unumApplicationId = :applicationId ")
	Integer updateApplicationOnVerification(@Param("applicationId") Long applicationId, @Param("applicationStatusSno") Long applicationStatusSno,
											@Param("levelId") Integer levelId, @Param("applicationStatusId") Integer applicationStatusId,
											@Param("decisionStatusId") Integer decisionStatusId,@Param("applicationStatusDate") Date applicationStatusDate);
}
