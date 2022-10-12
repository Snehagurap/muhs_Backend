package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CollegeFacultyMappingBean;
import in.cdac.university.globalService.service.CollegeFacultyMappingService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/collegeFaculty")
public class CollegeFacultyMappingController {

    @Autowired
    private CollegeFacultyMappingService collegeFacultyMappingService;

    @GetMapping("mapping/{collegeId}")
    public ResponseEntity<?> getMappingDetails(@PathVariable("collegeId") Long collegeId) throws Exception {
        return ResponseHandler.generateResponse(
                collegeFacultyMappingService.getMappingDetails(collegeId, RequestUtility.getUniversityId())
        );
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody CollegeFacultyMappingBean collegeFacultyMappingBean) throws Exception {
        collegeFacultyMappingBean.setUnumIsvalid(1);
        collegeFacultyMappingBean.setUnumEntryUid(RequestUtility.getUserId());
        collegeFacultyMappingBean.setUnumUnivId(RequestUtility.getUniversityId());
        collegeFacultyMappingBean.setUdtEntryDate(new Date());
        collegeFacultyMappingBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                collegeFacultyMappingService.saveMappingDetails(collegeFacultyMappingBean)
        );
    }

    @GetMapping("mappedFaculties/combo/{collegeId}")
    public ResponseEntity<?> getMappedFaculties(@PathVariable("collegeId") Long collegeId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(collegeFacultyMappingService.getMappedFaculties(collegeId, RequestUtility.getUniversityId()))
        );
    }
}
