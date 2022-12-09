package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ApplicationTrackerDtlBean;
import in.cdac.university.globalService.service.ApplicationTrackerService;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/applicationTracker")
public class ApplicationTrackerController {

    @Autowired
    ApplicationTrackerService applicationTrackerService;

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
}
