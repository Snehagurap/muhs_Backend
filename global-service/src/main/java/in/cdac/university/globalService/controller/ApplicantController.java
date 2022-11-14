package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.bean.DraftApplicantBean;
import in.cdac.university.globalService.service.ApplicantService;
import in.cdac.university.globalService.service.DraftApplicantService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/applicant")
public class ApplicantController {

    @Autowired
    private DraftApplicantService draftApplicantService;

    @Autowired
    private ApplicantService applicantService;

    @PostMapping("draft/save")
    public ResponseEntity<?> saveDraftApplicant(@Valid @RequestBody DraftApplicantBean draftApplicantBean) {
        draftApplicantBean.setUnumEntryUid(0L);
        draftApplicantBean.setUdtEffFrom(new Date());
        draftApplicantBean.setUdtEntryDate(new Date());
        draftApplicantBean.setUnumIsvalid(2);
        return ResponseHandler.generateResponse(
                draftApplicantService.save(draftApplicantBean)
        );
    }

    @PostMapping("draft/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestBody DraftApplicantBean draftApplicantBean) {
        return ResponseHandler.generateResponse(
                draftApplicantService.validateOtp(draftApplicantBean)
        );
    }

    @GetMapping("draft/{applicantId}")
    public ResponseEntity<?> getDraftApplicantById(@PathVariable("applicantId") Long applicantId) {
        return ResponseHandler.generateResponse(
                draftApplicantService.getApplicantById(applicantId)
        );
    }

    @PostMapping("details/save")
    public ResponseEntity<?> saveApplicantDetails(@Valid @RequestBody ApplicantBean applicantBean) throws Exception {
        applicantBean.setUnumIsvalid(1);
        applicantBean.setUdtEntryDate(new Date());
        applicantBean.setUdtEffFrom(new Date());
        applicantBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                applicantService.saveApplicantDetails(applicantBean)
        );
    }

    @GetMapping("verification/{isVerifiedApplicant}")
    public ResponseEntity<?> getListPageVerification(@PathVariable("isVerifiedApplicant") Integer isVerifiedApplicant) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        applicantService.getListPageVerification(isVerifiedApplicant)
                )
        );
    }

    @GetMapping("verificationView/{applicantId}")
    public ResponseEntity<?> getApplicant(@PathVariable("applicantId") Long applicantId) {
        return ResponseHandler.generateResponse(
          applicantService.getApplicant(applicantId)
        );
    }
}
