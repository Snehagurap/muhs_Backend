package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CourseBean;
import in.cdac.university.globalService.service.CourseService;
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
@RequestMapping("/global/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") int status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        courseService.listPageData(status)
                )
        );
    }

    @GetMapping("combo/{facultyId}")
    public ResponseEntity<?> getCousreByFacultyId(@PathVariable("facultyId") Integer facultyId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(courseService.getCousreByFacultyId(facultyId))
        );
    }

    @GetMapping("comboByCourseType/{courseTypeId}")
    public ResponseEntity<?> getCourseByCourseTypeId(@PathVariable("courseTypeId") Integer courseTypeId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(courseService.getCousreByCourseTypeId(courseTypeId))
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody CourseBean courseBean) throws Exception {
        courseBean.setUnumUnivId(RequestUtility.getUniversityId());
        courseBean.setUnumEntryUid(RequestUtility.getUserId());
        courseBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                courseService.save(courseBean)
        );
    }

    @GetMapping("{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable("courseId") Long courseId) throws Exception {
        return ResponseHandler.generateResponse(
                courseService.getCourse(courseId)
        );
    }

    // Put mapping replaced by POST
    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody CourseBean courseBean) throws Exception {
        courseBean.setUnumEntryUid(RequestUtility.getUserId());
        courseBean.setUnumUnivId(RequestUtility.getUniversityId());
        courseBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                courseService.update(courseBean)
        );
    }

    // Delete mapping replaced by POST
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        CourseBean courseBean = new CourseBean();
        courseBean.setUdtEntryDate(new Date());
        courseBean.setUnumUnivId(RequestUtility.getUniversityId());
        courseBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                courseService.delete(courseBean, idsToDelete)
        );
    }

    @PostMapping("combo/minRequiredCourse")
    public ResponseEntity<?> getMinRequiredCourse(@RequestBody Integer[] courseTypeIds) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(courseService.getMinReqCourseByCourseType(courseTypeIds))
        );
    }
}
