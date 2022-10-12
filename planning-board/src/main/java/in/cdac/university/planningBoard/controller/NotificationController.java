package in.cdac.university.planningBoard.controller;

import in.cdac.university.planningBoard.bean.NotificationBean;
import in.cdac.university.planningBoard.service.NotificationService;
import in.cdac.university.planningBoard.util.RequestUtility;
import in.cdac.university.planningBoard.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/pb/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("save")
    public ResponseEntity<?> saveNotification(@Valid @RequestBody NotificationBean notificationBean) throws Exception {
        notificationBean.setUnumIsvalid(1);
        notificationBean.setUdtEntryDate(new Date());
        notificationBean.setUnumEntryUid(RequestUtility.getUserId());
        notificationBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                notificationService.save(notificationBean)
        );
    }
}
