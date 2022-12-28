package in.cdac.university.globalService.controller;


import in.cdac.university.globalService.service.StreamService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
