package in.cdac.university.committee.controller;

import com.itextpdf.text.DocumentException;
import in.cdac.university.committee.bean.CommitteeBean;
import in.cdac.university.committee.bean.CommitteeMemberBean;
import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.service.CommitteeService;
import in.cdac.university.committee.service.LicCommitteeRuleSetMstService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/committee")
public class CommitteeController {

    @Autowired
    private CommitteeService committeeService;
    

    @PostMapping("creator/save")
    public ResponseEntity<?> createCommittee(@Valid @RequestBody CommitteeBean committeeBean) throws Exception {
        committeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeBean.setUnumIsvalid(1);
        committeeBean.setUnumEntryUid(RequestUtility.getUserId());
        Calendar cal = Calendar.getInstance();
        cal.setTime(committeeBean.getUdtComStartDate());
        cal.add(Calendar.DATE, committeeBean.getUnumComdurationDays());
        committeeBean.setUdtComEndDate(cal.getTime());
        committeeBean.setUstrComForyear(String.valueOf(cal.get(Calendar.YEAR)));
        return ResponseHandler.generateResponse(
                committeeService.createCommittee(committeeBean)
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getCommitteeCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        committeeService.getCommitteeCombo()
                )
        );
    }

    @GetMapping("creator/listPage/{committeeTypeId}")
    public ResponseEntity<?> getListPage(@PathVariable("committeeTypeId") Integer committeeTypeId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(committeeService.getCommitteeList(committeeTypeId))
        );
    }

    @GetMapping("{committeeId}")
    public ResponseEntity<?> getCommittee(@PathVariable("committeeId") Long committeeId) {
        return ResponseHandler.generateResponse(
                committeeService.getCommittee(committeeId)
        );
    }

    @GetMapping("withMembers/{eventId}")
    public ResponseEntity<?> getCommitteeByEventIdWithMembers(@PathVariable("eventId") Long eventId) {
        return ResponseHandler.generateResponse(
                committeeService.getCommitteeByEventIdWithMembers(eventId)
        );
    }

    @PostMapping("memberMapping/save")
    public ResponseEntity<?> saveMemberMapping(@Valid @RequestBody CommitteeMemberBean committeeMemberBean) {
        committeeMemberBean.setUnumEntryUid(RequestUtility.getUserId());
        committeeMemberBean.setUdtEntryDate(new Date());
        committeeMemberBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeMemberBean.setUnumIsvalid(1);
        return ResponseHandler.generateResponse(
                committeeService.saveMemberMapping(committeeMemberBean)
        );
    }

    @GetMapping("memberMapping/view/{eventId}")
    public ResponseEntity<?> getCommitteeMemberMappingByEventId(@PathVariable("eventId") Long eventId) {
        return ResponseHandler.generateResponse(
                committeeService.getCommitteeMemberMappingByEventId(eventId)
        );
    }

    @GetMapping("memberMapping/print/{eventId}")
    public ResponseEntity<?> generateCommitteeReport(@PathVariable("eventId") Long eventId) throws Exception {
        byte[] pdfBytes = committeeService.generateCommitteeReport(eventId);

        return ResponseHandler.generateFileResponse(pdfBytes, "committee_report.pdf");
    }
    

}
