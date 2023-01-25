package in.cdac.university.usm.controller;


import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.bean.DistrictBean;
import in.cdac.university.usm.service.DistrictService;
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
@RequestMapping("/usm/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/list/{stateCode}")
    public @ResponseBody ResponseEntity<?> getList(@PathVariable("stateCode") Integer stateCode) throws IllegalAccessException {
        List<ComboBean> comboBeans = new ArrayList<>();
        comboBeans.add(new ComboBean("", "Select Value"));
        comboBeans.addAll(ComboUtility.generateComboData(districtService.getAllDistricts(stateCode)));
        return ResponseHandler.generateOkResponse(comboBeans);
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<?> getAllDistricts() throws RuntimeException {
        return ResponseHandler.generateOkResponse(
                districtService.getAllDistricts()
        );
    }

    @GetMapping("listPage/{stateCode}/{status}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("stateCode") Integer stateCode, @PathVariable("status") Integer status) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(districtService.getDistrictsByStateCodeAndStatus(stateCode, status)));
    }

    //get the list of mapped and unmapped districts of maharashtra state
    @GetMapping("combo/{stateCode}")
    public @ResponseBody ResponseEntity<?> getDistricts(@PathVariable("stateCode") Integer stateCode) throws RuntimeException {
        return ResponseHandler.generateOkResponse(
                districtService.getDistricts(stateCode)
        );
    }

    //get the district by district Id
    @GetMapping("getDistrict/{distCode}")
    public ResponseEntity<?> getDistrictById(@PathVariable("distCode") Integer distCode) throws RuntimeException {
        return ResponseHandler.generateResponse(
                districtService.getDistrictById(distCode)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveDistrictDtl(@Valid @RequestBody DistrictBean districtBean)  {
        districtBean.setGnumSlno(1);
        return ResponseHandler.generateResponse(districtService.save(districtBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody DistrictBean districtBean) throws Exception {
        return ResponseHandler.generateResponse(
                districtService.update(districtBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Integer[] idsToDelete) throws Exception {
        return ResponseHandler.generateResponse(
                districtService.delete(idsToDelete)
        );
    }

}
