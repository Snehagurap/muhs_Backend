package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ProcessBean;
import in.cdac.university.globalService.service.ProcessService;
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
@RequestMapping("/global/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getProcessCombo() throws Exception{
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(processService.getAllProcess())
        );
    }

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") Integer status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        processService.listPageData(status)
                )
        );
    }

    @GetMapping("{processId}")
    public ResponseEntity<?> getProcess(@PathVariable("processId") Integer processId) {
        return ResponseHandler.generateResponse(
                processService.getProcess(processId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveProcessDtl(@Valid @RequestBody ProcessBean processBean) throws Exception {
        Date d1 = new Date();
        processBean.setUdtEntryDate(d1);
        processBean.setUdtEffFrom(d1);
        processBean.setUnumUnivId(RequestUtility.getUniversityId());
        processBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(processService.save(processBean));
    }


    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody ProcessBean processBean) throws Exception {
        Date d1 = new Date();
        processBean.setUdtEntryDate(d1);
        processBean.setUdtEffFrom(d1);
        return ResponseHandler.generateResponse(
                processService.update(processBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Integer[] idsToDelete) throws Exception {
        ProcessBean processBean = new ProcessBean();
        processBean.setUdtEntryDate(new Date());
        processBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                processService.delete(processBean, idsToDelete)
        );
    }
}
