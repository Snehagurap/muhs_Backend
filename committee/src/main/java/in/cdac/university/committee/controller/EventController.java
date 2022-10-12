package in.cdac.university.committee.controller;

import in.cdac.university.committee.bean.EventBean;
import in.cdac.university.committee.service.EventService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/committee/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("save")
    public ResponseEntity<?> saveEvent(@Valid @RequestBody EventBean eventBean) {
        eventBean.setUnumIsvalid(1);
        eventBean.setUnumEntryUid(RequestUtility.getUserId());
        eventBean.setUdtEntryDate(new Date());
        eventBean.setUnumUnivId(RequestUtility.getUniversityId());

        return ResponseHandler.generateResponse(eventService.save(eventBean));
    }

    @GetMapping("combo")
    public ResponseEntity<?> getEventCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(eventService.getEventCombo())
        );
    }

    @GetMapping("combo/{committeeId}")
    public ResponseEntity<?> getEventComboByCommitteeId(@PathVariable("committeeId") Long committeeId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(eventService.getEventCombo(committeeId))
        );
    }

    @GetMapping("listPage/{committeeId}")
    public ResponseEntity<?> listPage(@PathVariable("committeeId") Long committeeId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        eventService.getListPageData(committeeId)
                )
        );
    }

    @GetMapping("{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable("eventId") Long eventId) {
        return ResponseHandler.generateResponse(
                eventService.getEvent(eventId)
        );
    }
}
