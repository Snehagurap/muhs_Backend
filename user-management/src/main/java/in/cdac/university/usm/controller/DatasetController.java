package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.DatasetBean;
import in.cdac.university.usm.service.DatasetService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/usm/dataset")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getList() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(datasetService.getAllDataset())
        );
    }

    @GetMapping("schemawisemodulelist")
    public @ResponseBody ResponseEntity<?> getSchemaWiseModuleList() throws  IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(datasetService.getSchemaWiseModuleList())
        );
    }

    @GetMapping("tablelist/{schemaName}")
    @ApiOperation("Get table combo on the basis of schema name")
    public @ResponseBody ResponseEntity<?> getTableListBySchemaName(@PathVariable("schemaName") String schemaName) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(datasetService.getTableListBySchemaName(schemaName))
             
        );
    }

    @GetMapping("columnlist/{tableName}/{schemaName}")@ApiOperation("Get the list of columns on the basis of the table")
    public @ResponseBody ResponseEntity<?> getColumnListByTableName(@PathVariable("tableName") String tableName, @PathVariable("schemaName") String schemaName) throws  IllegalAccessException {
       return ResponseHandler.generateOkResponse(
               ComboUtility.generateComboData(datasetService.getColumnListByTableName(tableName,schemaName))
       );
    }

    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody DatasetBean datasetBean) throws Exception{
        datasetBean.setGnumEntryBy(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(datasetService.save(datasetBean));
    }

    @GetMapping("listPage/{moduleId}/{isValid}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("moduleId") Integer moduleId,
                                                       @PathVariable("isValid") Integer isValid) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(datasetService.getListPage(moduleId,isValid))
        );

    }

    @GetMapping("{datasetId}")
    public @ResponseBody ResponseEntity<?> getDatasetById(@PathVariable("datasetId") Integer datasetId) {
        return ResponseHandler.generateResponse(datasetService.getDatasetById(datasetId));
    }


    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody DatasetBean datasetBean) throws  Exception {
        datasetBean.setGnumEntryBy(RequestUtility.getUserId());
        datasetBean.setGdtEntryDate(new Date());

        return ResponseHandler.generateResponse(datasetService.update(datasetBean));
    }

    @DeleteMapping("delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws  Exception {
        DatasetBean datasetBean = new DatasetBean();
        datasetBean.setIdsToDelete(idsToDelete);
        datasetBean.setGnumEntryBy(545654L);
        datasetBean.setGdtEntryDate(new Date());
        return ResponseHandler.generateResponse(datasetService.delete(datasetBean));
    }
}
