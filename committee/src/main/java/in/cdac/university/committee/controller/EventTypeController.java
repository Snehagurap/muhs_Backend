package in.cdac.university.committee.controller;

import in.cdac.university.committee.service.EventTypeService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/committee/eventType")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @GetMapping("combo")
    public ResponseEntity<?> getEventTypeCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        eventTypeService.getEventTypes()
                )
        );
    }
}
