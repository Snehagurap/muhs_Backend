package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.NotificationStyleService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/notificationStyle")
public class NotificationStyleController {

    @Autowired
    private NotificationStyleService notificationStyleService;

    @GetMapping("combo")
    public ResponseEntity<?> getNotificationReceiptCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(notificationStyleService.getAllNotificationStyles())
        );
    }
}
