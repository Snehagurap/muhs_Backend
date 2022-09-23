package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.service.GlobalService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/global")
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @GetMapping("academicYear/combo")
    public ResponseEntity<?> getAcademicYearCombo() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        List<ComboBean> academicYears = IntStream.range(-1, 3)
                .map(value -> currentYear + value)
                .boxed()
                .map(String::valueOf)
                .map(year -> new ComboBean(year, year))
                .toList();

        return ResponseHandler.generateOkResponse(
                academicYears
        );
    }

    @GetMapping("language/combo")
    public ResponseEntity<?> getAllLanguages() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(globalService.getAllLanguages())
        );
    }
}
