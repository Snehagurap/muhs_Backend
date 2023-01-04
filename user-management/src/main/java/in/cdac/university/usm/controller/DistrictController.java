package in.cdac.university.usm.controller;


import in.cdac.university.usm.service.DistrictService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usm/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/list/{stateCode}")
    public @ResponseBody ResponseEntity<?> getList(@PathVariable("stateCode") Integer stateCode) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(districtService.getAllDistricts(stateCode))
        );
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<?> getAllDistricts() throws RuntimeException{
        return ResponseHandler.generateOkResponse(
                districtService.getAllDistricts()
        );
    }

    @GetMapping("/listPage/{stateCode}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("stateCode") Integer stateCode) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(districtService.getAllDistricts(stateCode)));
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
}
