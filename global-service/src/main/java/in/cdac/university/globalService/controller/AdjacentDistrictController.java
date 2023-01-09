package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.AdjacentDistrictBean;
import in.cdac.university.globalService.service.AdjacentDistrictService;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/adjacentDistrict")
public class AdjacentDistrictController {

    @Autowired
    private AdjacentDistrictService adjacentDistrictService;

    @GetMapping("combo/{stateCode}/{distCode}")
    @ApiOperation(value = "Get the list of adjacent districts mapped and not mapped to the district")
    public @ResponseBody ResponseEntity<?> getAdjacentDistricts(@PathVariable("stateCode") Integer stateCode,
                                                                @PathVariable("distCode") Integer distCode) throws  IllegalAccessException {
        return ResponseHandler.generateResponse(adjacentDistrictService.getAdjacentDistricts(stateCode,distCode));
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody AdjacentDistrictBean adjacentDistrictBean) throws Exception {
        adjacentDistrictBean.setUnumIsvalid(1);
        adjacentDistrictBean.setUnumEntryUid(RequestUtility.getUserId());
        adjacentDistrictBean.setUdtEntryDate(new Date());
        adjacentDistrictBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                adjacentDistrictService.saveMappingDetails(adjacentDistrictBean)
        );
    }
}
