package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.service.TemplateItemService;
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
@RequestMapping("/global/template/item")
public class TemplateItemController {

    @Autowired
    private TemplateItemService templateItemService;

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateItemBean templateItemBean) throws Exception {
        templateItemBean.setUnumIsvalid(1);
        templateItemBean.setUnumEntryUid(RequestUtility.getUserId());
        templateItemBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateItemBean.setUdtEntryDate(new Date());
        templateItemBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                templateItemService.save(templateItemBean)
        );
    }

    @GetMapping("id/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable("itemId") Long itemId) {
        return ResponseHandler.generateResponse(
                templateItemService.getItemById(itemId)
        );
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateItemBean templateItemBean) throws Exception {
        templateItemBean.setUnumIsvalid(1);
        templateItemBean.setUnumEntryUid(RequestUtility.getUserId());
        templateItemBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateItemBean.setUdtEntryDate(new Date());
        templateItemBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                templateItemService.update(templateItemBean)
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        TemplateItemBean templateItemBean = new TemplateItemBean();
        templateItemBean.setUdtEntryDate(new Date());
        templateItemBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateItemBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateItemService.delete(templateItemBean, idsToDelete)
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getItemCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        templateItemService.getAllActiveItems(RequestUtility.getUniversityId())
                )
        );
    }

    @GetMapping("listPage")
    public ResponseEntity<?> listPageData() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        templateItemService.listPageData(RequestUtility.getUniversityId())
                )
        );
    }
}
