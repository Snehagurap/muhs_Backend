package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.service.SubjectService;
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
@RequestMapping("/global/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("combo")
    public ResponseEntity<?> getAllSubjectCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(subjectService.getSubjects())
        );
    }

    @GetMapping("comboByCourseId/{courseId}")
    public ResponseEntity<?> getSubjectByCousreId(@PathVariable("courseId") Long courseId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(subjectService.getSubjectByCousreId(courseId))
        );
    }

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") Integer status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        subjectService.getAllSubjects(status)
                )
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody SubjectBean subjectBean) throws Exception {
        subjectBean.setUnumEntryUid(RequestUtility.getUserId());
        subjectBean.setUnumUnivId(RequestUtility.getUniversityId());
        subjectBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                subjectService.save(subjectBean)
        );
    }

    @GetMapping("{subjectId}")
    public ResponseEntity<?> getSubject(@PathVariable("subjectId") Long subjectId) throws Exception {

        return ResponseHandler.generateResponse(
                subjectService.getSubject(subjectId)
        );
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody SubjectBean subjectBean) throws Exception {
        subjectBean.setUnumEntryUid(RequestUtility.getUserId());
        subjectBean.setUnumUnivId(RequestUtility.getUniversityId());
        subjectBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                subjectService.update(subjectBean)
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        SubjectBean subjectBean = new SubjectBean();
        subjectBean.setUnumEntryUid(RequestUtility.getUserId());
        subjectBean.setUnumUnivId(RequestUtility.getUniversityId());
        subjectBean.setUdtEntryDate(new Date());
        subjectBean.setIdsToDelete(idsToDelete);
        return ResponseHandler.generateResponse(
                subjectService.delete(subjectBean)
        );
    }
}
