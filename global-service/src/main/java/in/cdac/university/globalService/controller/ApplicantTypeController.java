package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.service.ApplicantTypeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/global/applicant/type")
public class ApplicantTypeController {

    @Autowired
    private ApplicantTypeService applicantTypeService;

    @GetMapping("combo")
    public ResponseEntity<?> getApplicantTypeCombo() throws IllegalAccessException {
        List<ComboBean> applicantTypes = new ArrayList<>();
        applicantTypes.add(new ComboBean("", "Select Value"));
        applicantTypes.addAll(ComboUtility.generateComboData(applicantTypeService.getApplicantTypes()));
        return ResponseHandler.generateOkResponse(applicantTypes);
    }
}
