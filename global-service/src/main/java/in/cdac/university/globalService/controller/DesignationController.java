package in.cdac.university.globalService.controller;


import in.cdac.university.globalService.bean.DesignationBean;
import in.cdac.university.globalService.service.DesignationService;
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
@RequestMapping("/global/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;


    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getDesignationCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(designationService.getAllDesignations())
        );
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getDesignation(@PathVariable("postId") Integer postId) throws Exception {
        return ResponseHandler.generateResponse(
                designationService.getDesignation(postId)
        );
    }

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") int status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        designationService.listPageData(status)
                )
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> savePostDtl(@Valid @RequestBody DesignationBean designationBean) throws Exception {
        designationBean.setUnumUnivId(RequestUtility.getUniversityId());
        designationBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(designationService.save(designationBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody DesignationBean designationBean) throws Exception {
        designationBean.setUnumUnivId(RequestUtility.getUniversityId());
        designationBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                designationService.update(designationBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Integer[] idsToDelete) throws Exception {
        DesignationBean designationBean = new DesignationBean();
        designationBean.setUdtEntryDate(new Date());
        designationBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                designationService.delete(designationBean, idsToDelete)
        );
    }


}
