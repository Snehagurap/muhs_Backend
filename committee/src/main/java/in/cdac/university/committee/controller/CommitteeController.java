package in.cdac.university.committee.controller;

import in.cdac.university.committee.bean.CommitteeBean;
import in.cdac.university.committee.service.CommitteeService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

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

    @GetMapping("creator/listPage")
    public ResponseEntity<?> getListPage() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(committeeService.getCommitteeList())
        );
    }
}
