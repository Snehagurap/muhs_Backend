package in.cdac.university.planningBoard.controller;

import in.cdac.university.planningBoard.bean.NotificationBean;
import in.cdac.university.planningBoard.service.NotificationService;
import in.cdac.university.planningBoard.util.ComboUtility;
import in.cdac.university.planningBoard.util.ListPageUtility;
import in.cdac.university.planningBoard.util.RequestUtility;
import in.cdac.university.planningBoard.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("listPage/{year}")
    public ResponseEntity<?> listPage(@PathVariable("year") String year) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(notificationService.getListPageData(year))
        );
    }

    @GetMapping("{notificationId}")
    public ResponseEntity<?> getNotificationById(@PathVariable("notificationId") Long notificationId) throws Exception {
        return ResponseHandler.generateResponse(
                notificationService.getNotificationById(notificationId)
        );
    }

    // Put mapping replaced by POST
    @PostMapping("update")
    public ResponseEntity<?> updateNotification(@Valid @RequestBody NotificationBean notificationBean) throws Exception {
        notificationBean.setUnumIsvalid(1);
        notificationBean.setUdtEntryDate(new Date());
        notificationBean.setUnumEntryUid(RequestUtility.getUserId());
        notificationBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                notificationService.update(notificationBean)
        );
    }

    // Delete mapping replaced by POST
    @PostMapping("delete")
    public ResponseEntity<?> deleteNotification(@RequestBody Long[] idsToDelete) throws Exception {
        NotificationBean notificationBean = new NotificationBean();
        notificationBean.setUnumIsvalid(1);
        notificationBean.setUdtEntryDate(new Date());
        notificationBean.setUnumEntryUid(RequestUtility.getUserId());
        notificationBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                notificationService.delete(notificationBean, idsToDelete)
        );
    }

    @GetMapping("active")
    public ResponseEntity<?> getActiveNotifications() throws Exception {
        return ResponseHandler.generateResponse(
                notificationService.getActiveNotifications()
        );
    }

    @GetMapping("notificationCombo/{year}")
    public ResponseEntity<?> getNotificationComboByYear(@PathVariable("year") String year) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(notificationService.getNotificationComboByYear(year)
        ));
    }
}
