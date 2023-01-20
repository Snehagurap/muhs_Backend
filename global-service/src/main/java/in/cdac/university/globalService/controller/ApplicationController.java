package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.ApplicationService;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("byUser")
    public ResponseEntity<?> getApplicationsByUser() throws Exception {
        return ResponseHandler.generateOkResponse(
                applicationService.getApplicationsByUser(RequestUtility.getUserId())
        );
    }
    
    @GetMapping("/id/{Id}")
    public ResponseEntity<?> getApplicationByunumApplicationId(@PathVariable("Id") Long unumApplicationId) throws Exception {
        return ResponseHandler.generateResponse(
               applicationService.getApplicationByunumApplicationId(unumApplicationId)
        );
    }

    @GetMapping("{notificationId}")
    public ResponseEntity<?> getApplicationByNotificationId(@PathVariable("notificationId") Long notificationId) throws Exception {
        return ResponseHandler.generateResponse(
                applicationService.getApplicationDetailByNotificationId(notificationId)
        );
    }
}
