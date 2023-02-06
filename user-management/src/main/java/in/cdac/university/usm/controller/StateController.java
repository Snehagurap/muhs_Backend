package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.bean.StateBean;
import in.cdac.university.usm.service.StateService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usm/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping("/list/{countryCode}")
    public @ResponseBody ResponseEntity<?> getList(@PathVariable("countryCode") Integer countryCode) throws IllegalAccessException {
        List<ComboBean> comboBeans = new ArrayList<>();
        comboBeans.add(new ComboBean("", "Select Value"));
        comboBeans.addAll(ComboUtility.generateComboData(stateService.getAllStates(countryCode)));
        return ResponseHandler.generateOkResponse(comboBeans);
    }

    @GetMapping("listPage/{countryCode}/{status}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("countryCode") Integer countryCode, @PathVariable("status") Integer status) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(stateService.getListPage(countryCode, status)));
    }


    @GetMapping("stateCombo")
    public @ResponseBody ResponseEntity<?> getStateByStateCode() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(stateService.getStateByStateCode())
        );
    }

    @GetMapping("getStateById/{stateCode}")
    public ResponseEntity<?> getStateById(@PathVariable("stateCode") Integer stateCode) {
        return ResponseHandler.generateResponse(
                stateService.getStateById(stateCode)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveDistrictDtl(@Valid @RequestBody StateBean stateBean)  {
        return ResponseHandler.generateResponse(stateService.save(stateBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody StateBean stateBean) throws Exception {
        return ResponseHandler.generateResponse(
                stateService.update(stateBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Integer[] idsToDelete) throws Exception {
        return ResponseHandler.generateResponse(
                stateService.delete(idsToDelete)
        );
    }

}
