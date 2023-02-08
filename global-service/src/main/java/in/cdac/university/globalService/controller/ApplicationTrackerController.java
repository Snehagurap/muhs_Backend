package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ApplicationTrackerBean;
import in.cdac.university.globalService.bean.ApplicationTrackerDtlBean;
import in.cdac.university.globalService.service.ApplicationTrackerService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/applicationTracker")
public class ApplicationTrackerController {

    @Autowired
    private ApplicationTrackerService applicationTrackerService;

    @PostMapping("scrutiny")
    public ResponseEntity<?> applicationScrutiny(@Valid @RequestBody ApplicationTrackerDtlBean applicationTrackerDtlBean) throws Exception {
        applicationTrackerDtlBean.setUdtApplicationStatusDt(new Date());
        applicationTrackerDtlBean.setUdtEffFrom(new Date());
        applicationTrackerDtlBean.setUnumEntryUid(RequestUtility.getUserId());
        applicationTrackerDtlBean.setUdtEntryDate(new Date());
        applicationTrackerDtlBean.setUnumUnivId(RequestUtility.getUniversityId());
        applicationTrackerDtlBean.setUnumIsvalid(1);
        //applicationTrackerDtlBean.setUnumStatusBy(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                applicationTrackerService.applicationScrutiny(applicationTrackerDtlBean)
        );
    }


    @GetMapping("scrutinyDetail/planningBoard/{applicationId}/{applicationStatus}")
    public ResponseEntity<?> getScrutinyDetailPlanningBoard(@PathVariable("applicationId") Long applicationId,
                                                            @PathVariable("applicationStatus") Integer applicationStatus) {
        return ResponseHandler.generateResponse(
                applicationTrackerService.getScrutinyDetailPlanningBoard(applicationId, applicationStatus)
        );
    }

    @GetMapping("department/{notificationId}/{notificationDetailId}/{levelId}/{finYear}/{streamId}")
    public ResponseEntity<?> getApplicationForDepartmentScrutiny(@PathVariable("notificationId") Long notificationId,
                                           @PathVariable("notificationDetailId") Long notificationDetailId,
                                           @PathVariable("levelId") String levelId, @PathVariable("finYear") String finYear,
                                           @PathVariable("streamId") Long streamId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                    applicationTrackerService.getApplicationForDepartmentScrutiny(notificationId, notificationDetailId, levelId, finYear,streamId))
        );
    }


    @PostMapping("update/status/receivedBy")
    public ResponseEntity<?> updateStatusForApplicationReceived(@RequestBody ApplicationTrackerBean applicationTrackerBean) throws Exception {
        return ResponseHandler.generateResponse(
                applicationTrackerService.updateStatusForApplicationReceived(applicationTrackerBean.getUnumApplicationId(), applicationTrackerBean.getUnumApplicationLevelId())
        );
    }

    @PostMapping("verify/application")
    public ResponseEntity<?> verifyApplication(@RequestBody ApplicationTrackerDtlBean applicationTrackerDtlBean) throws Exception {
        return ResponseHandler.generateResponse(
                applicationTrackerService.verifyApplication(applicationTrackerDtlBean)
        );
    }

    @GetMapping("verified/detail/{applicationId}/{levelId}")
    public ResponseEntity<?> getVerifiedDepartmentDtl(@PathVariable("applicationId") Long applicationId, @PathVariable("levelId") Integer levelId) throws Exception {
        return ResponseHandler.generateResponse(
                applicationTrackerService.getVerifiedDetails(applicationId,levelId)
        );
    }
}
