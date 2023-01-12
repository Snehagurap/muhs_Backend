package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateItemApiBean;
import in.cdac.university.globalService.service.TemplateItemApiService;
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
@RequestMapping("/global/template/item/api")
public class TemplateItemApiController {
    @Autowired
    TemplateItemApiService templateItemApiService;

    @GetMapping("combo")
    public ResponseEntity<?> getTemplateItemApiCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(templateItemApiService.getAllTemplateItemApis())
        );
    }

    @GetMapping("{apiId}")
    public ResponseEntity<?> getTemplateItemApi(@PathVariable("apiId") Long apiId) {
        return ResponseHandler.generateResponse(
                templateItemApiService.getTemplateItemApi(apiId)
        );
    }

    @GetMapping("listPage")
    public ResponseEntity<?> listPageData() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        templateItemApiService.listPageData()
                )
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveTemplateItemApiDtl(@Valid @RequestBody TemplateItemApiBean templateItemApiBean) throws Exception {
        templateItemApiBean.setUdtEntryDate(new Date());
        templateItemApiBean.setUnumEntryUid(RequestUtility.getUserId());
        templateItemApiBean.setUnumApiTypeId(1);
        templateItemApiBean.setUnumIsvalid(1);
        System.out.println("--->>> Bean " + templateItemApiBean);
        return ResponseHandler.generateResponse(templateItemApiService.save(templateItemApiBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateItemApiBean templateItemApiBean) throws Exception {
        templateItemApiBean.setUdtEntryDate(new Date());
        templateItemApiBean.setUnumEntryUid(RequestUtility.getUserId());
        templateItemApiBean.setUnumIsvalid(1);
        templateItemApiBean.setUnumApiTypeId(1);
        return ResponseHandler.generateResponse(
                templateItemApiService.update(templateItemApiBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        TemplateItemApiBean templateItemApiBean = new TemplateItemApiBean();
        templateItemApiBean.setUdtEntryDate(new Date());
        templateItemApiBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateItemApiService.delete(templateItemApiBean, idsToDelete)
        );
    }

}
