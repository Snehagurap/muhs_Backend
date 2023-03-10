package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.NotificationTypeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/notificationType")
public class NotificationTypeController {

    @Autowired
    private NotificationTypeService notificationTypeService;

    @GetMapping("combo")
    public ResponseEntity<?> getNotificationTypeCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(notificationTypeService.getAllValidNotificationTypes())
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllNotificationType() throws Exception {
        return ResponseHandler.generateOkResponse(notificationTypeService.getAllNotificationTypes());
    }

    @GetMapping("{notificationTypeId}")
    public ResponseEntity<?> getNotificationTypeById(@PathVariable("notificationTypeId") Integer notificationTypeId) {
        return ResponseHandler.generateResponse(notificationTypeService.getNotificationTypeById(notificationTypeId));
    }
}
