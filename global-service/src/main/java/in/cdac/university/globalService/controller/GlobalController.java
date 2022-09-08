package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/global")
public class GlobalController {

    @GetMapping("academicYear/combo")
    public ResponseEntity<?> getAcademicYearCombo() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        List<Integer> academicYears = IntStream.range(-1, 3)
                .map(value -> currentYear + value)
                .boxed()
                .toList();

        return ResponseHandler.generateOkResponse(
                academicYears
        );
    }
}
