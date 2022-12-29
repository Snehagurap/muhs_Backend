package in.cdac.university.globalService.controller;

 
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.service.StreamService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


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
	
	
	
	@PostMapping("save")
    public ResponseEntity<?> saveStreamDetails(@Valid @RequestBody StreamBean streamBean) throws Exception {
		streamBean.setUnumIsvalid(1);
		streamBean.setUdtEntryDate(new Date());
		streamBean.setUdtEffFrom(new Date());
		streamBean.setUnumEntryUid(RequestUtility.getUserId());
		streamBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                streamService.save(streamBean)
       );
		//return null;
    }
	

}
