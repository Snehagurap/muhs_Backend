package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CoursePatternBean;
import in.cdac.university.globalService.service.CoursePatternService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/coursePattern")
public class CoursePatternController {

    @Autowired
    private CoursePatternService coursePatternService;

    @GetMapping("{coursePatId}")
    public ResponseEntity<?> getCoursePattern(@PathVariable("coursePatId") Long coursePatId) {
        return ResponseHandler.generateResponse(
                coursePatternService.getCoursePattern(coursePatId)
        );
    }

    @GetMapping("listPage")
    public ResponseEntity<?> listPage() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        coursePatternService.listPage()
                )
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveCoursePatternDtl(@Valid @RequestBody CoursePatternBean coursePatternBean) throws Exception {
        coursePatternBean.setUdtEntryDate(new Date());
        coursePatternBean.setUnumEntryUid(RequestUtility.getUserId());
        coursePatternBean.setUnumUnivId(RequestUtility.getUniversityId());
        coursePatternBean.setUnumIsvalid(1);
        return ResponseHandler.generateResponse(coursePatternService.save(coursePatternBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody CoursePatternBean coursePatternBean) throws Exception {
        coursePatternBean.setUdtEntryDate(new Date());
        coursePatternBean.setUnumEntryUid(RequestUtility.getUserId());
        coursePatternBean.setUnumUnivId(RequestUtility.getUniversityId());
        coursePatternBean.setUnumIsvalid(1);
        return ResponseHandler.generateResponse(
                coursePatternService.update(coursePatternBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        CoursePatternBean coursePatternBean = new CoursePatternBean();
        coursePatternBean.setUdtEntryDate(new Date());
        coursePatternBean.setUnumEntryUid(RequestUtility.getUserId());
        coursePatternBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                coursePatternService.delete(coursePatternBean, idsToDelete)
        );
    }

}
