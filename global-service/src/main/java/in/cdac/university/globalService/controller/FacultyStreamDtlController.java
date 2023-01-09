package in.cdac.university.globalService.controller;


import in.cdac.university.globalService.bean.FacultyStreamDtlBean;
import in.cdac.university.globalService.service.FacultyStreamDtlService;
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
@RequestMapping("/global/facultyStreamDtl")
public class FacultyStreamDtlController {

    @Autowired
    FacultyStreamDtlService facultyStreamDtlService;

    @GetMapping("combo/{facultyId}")
    public ResponseEntity<?> getStreamComboByFaculty(@PathVariable("facultyId") Integer facultyId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(facultyStreamDtlService.getStreamComboByFaculty(facultyId))
        );
    }

    @GetMapping("mappedUnmappedCombo/{facultyId}")
    public ResponseEntity<?> getStreams(@PathVariable("facultyId") Integer facultyId) throws Exception {

        return ResponseHandler.generateResponse(facultyStreamDtlService.getStreamsByFacultyId(facultyId));
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody FacultyStreamDtlBean facultyStreamDtlBean) throws Exception {
        facultyStreamDtlBean.setUnumIsvalid(1);
        facultyStreamDtlBean.setUnumEntryUid(RequestUtility.getUserId());
        facultyStreamDtlBean.setUnumUnivId(RequestUtility.getUniversityId());
        facultyStreamDtlBean.setUdtEffFrom(new Date());
        facultyStreamDtlBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(facultyStreamDtlService.saveMappingDetails(facultyStreamDtlBean));
    }

    @GetMapping("listPage")
    public @ResponseBody ResponseEntity<?> getFacultyStreamList() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(facultyStreamDtlService.getFacultyStreamList()));


    }
 }
