package in.cdac.university.globalService.controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.globalService.service.StreamService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;


@RestController
@RequestMapping("/global/stream")
public class StreamController {

    @Autowired
    private StreamService streamService;
  
    @GetMapping("combo")
    public ResponseEntity<?> getStreamCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(streamService.getStreamCombo())
        );
    }
    
	@GetMapping("listPage/data/{IsValid}")
    public ResponseEntity<?> listStream(@PathVariable("IsValid") int IsValid) throws Exception {
       
		return ResponseHandler.generateOkResponse(
                		streamService.getAllStream(IsValid)
        );
    }
	
	
	/*
	@PostMapping("save")
    public ResponseEntity<?> saveStreamDetails(@Valid @RequestBody StreamBean streamBean) throws Exception {
        applicantBean.setUnumIsvalid(1);
        applicantBean.setUdtEntryDate(new Date());
        applicantBean.setUdtEffFrom(new Date());
        applicantBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                applicantService.saveApplicantDetails(applicantBean)
        );
    }
	*/

}
