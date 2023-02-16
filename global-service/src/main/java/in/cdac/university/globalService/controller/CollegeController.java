package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.service.CollegeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> getListPageData(@PathVariable("status") Integer status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(collegeService.listPageData(status))
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getCollegeCombo () throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        collegeService.getColleges()
                )
        );
    }

    @GetMapping("{collegeId}")
    public ResponseEntity<?> getCollegeById (@PathVariable("collegeId") Long collegeId) throws Exception {
        return ResponseHandler.generateResponse(
                collegeService.getCollegeById(collegeId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody CollegeBean collegeBean) throws Exception {
        collegeBean.setUdtEntryDate(new Date());
        collegeBean.setUnumUnivId(RequestUtility.getUniversityId());
        collegeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                collegeService.save(collegeBean)
        );
    }

    // Put mapping replaced by POST
@PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody CollegeBean collegeBean) throws Exception {
        collegeBean.setUdtEntryDate(new Date());
        collegeBean.setUdtEffFrom(new Date());
        collegeBean.setUnumUnivId(RequestUtility.getUniversityId());
        collegeBean.setUnumEntryUid(RequestUtility.getUserId());

        return ResponseHandler.generateResponse(
                collegeService.update(collegeBean)
        );
    }

    // Delete mapping replaced by POST
@PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        CollegeBean collegeBean = new CollegeBean();
        collegeBean.setUdtEntryDate(new Date());
        collegeBean.setUnumUnivId(RequestUtility.getUniversityId());
        collegeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                collegeService.delete(collegeBean, idsToDelete)
        );
    }

    @GetMapping("count")
    public ResponseEntity<?> totalNoOfColleges() {
        return ResponseHandler.generateResponse(
                collegeService.totalNoOfColleges()
        );
    }
}
