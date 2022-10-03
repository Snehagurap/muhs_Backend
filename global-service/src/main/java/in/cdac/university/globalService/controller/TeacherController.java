package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.TeacherService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") int status) throws Exception {
        return ResponseHandler.generateOkResponse(
                teacherService.listPageData(status)
        );
    }

    @GetMapping("committeeMember/combo/{eventId}")
    public ResponseEntity<?> combo(@PathVariable("eventId") Long eventId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(teacherService.getCommitteeMembers(eventId))
        );
    }
}
