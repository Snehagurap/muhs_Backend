package in.cdac.university.globalService.controller;

import java.util.Date;

import javax.validation.Valid;

import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.annotations.ComboValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.bean.StreamBean;
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

    @GetMapping("combo/withFacultyName")
    public ResponseEntity<?> getStreamComboWithFacultyName() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(streamService.getStreamComboWithFacultyName())
        );
    }

    @GetMapping("listPage/data/{IsValid}")
    public ResponseEntity<?> listStream(@PathVariable("IsValid") int IsValid) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(streamService.getAllStream(IsValid))
        );
    }

    @GetMapping("byId/{streamId}")
    public ResponseEntity<?> getById(@PathVariable("streamId") Long streamId) {
        return ResponseHandler.generateOkResponse(
                streamService.getStreamById(streamId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveStreamDetails(@Valid @RequestBody StreamBean streamBean) throws Exception {
        streamBean.setUdtEntryDate(new Date());
        streamBean.setUdtEffFrom(new Date());
        streamBean.setUnumEntryUid(RequestUtility.getUserId());
        streamBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                streamService.save(streamBean)
        );
    }

    @PostMapping("update")
    public ResponseEntity<?> updateStreamDetails(@Valid @RequestBody StreamBean streamBean) throws Exception {
        streamBean.setUnumEntryUid(RequestUtility.getUserId());
        streamBean.setUnumUnivId(RequestUtility.getUniversityId());
        Date currentDate = new Date();
        streamBean.setUdtEntryDate(currentDate);
        streamBean.setUdtEffFrom(currentDate);
        return ResponseHandler.generateResponse(
                streamService.updateStreamDetails(streamBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteStreamDetails(@RequestBody Long[] idsToDelete) throws Exception {
        StreamBean streamBean = new StreamBean();
        streamBean.setUnumUnivId(RequestUtility.getUniversityId());
        streamBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                streamService.deleteStreamDetails(streamBean, idsToDelete)
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllStreams() {
        return ResponseHandler.generateOkResponse(streamService.getAllStreams());
    }

}
