package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.service.DropdownService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/global")
public class CommonController {

    @Autowired
    private DropdownService dropdownService;

    @GetMapping("columns/{className}")
    public ResponseEntity<?> getColumns(@PathVariable("className") String className) throws ClassNotFoundException {
        String decodedClassName = new String(Base64.getDecoder().decode(className.getBytes()));
        Class<?> clazz = Class.forName(decodedClassName);
        return ResponseHandler.generateOkResponse(ListPageUtility.getColumns(clazz));
    }

    @GetMapping("modeOfPayment")
    public ResponseEntity<?> modeOfPayment() {
        List<ComboBean> modeOfPayments = List.of(
                new ComboBean("", "Select Value"),
                new ComboBean("1", "RTGS"),
                new ComboBean("2", "NEFT"),
                new ComboBean("3", "Demand Draft"),
                new ComboBean("4", "Online")
        );
        return ResponseHandler.generateOkResponse(modeOfPayments);
    }

    @GetMapping("areaType")
    public ResponseEntity<?> areaType() {
        List<ComboBean> areaTypes = List.of(
                new ComboBean("", "Select Value"),
                new ComboBean("1", "Village"),
                new ComboBean("2", "Tehsil"),
                new ComboBean("3", "District")
        );
        return ResponseHandler.generateOkResponse(areaTypes);
    }

    @GetMapping("dropdown/{dropdownId}")
    public ResponseEntity<?> dropdown(@PathVariable("dropdownId") Long dropdownId) throws IllegalAccessException {
        List<ComboBean> dropdown = new ArrayList<>();
        dropdown.add(new ComboBean("", "Select Value"));
        dropdown.addAll(ComboUtility.generateComboData(dropdownService.getDropdown(dropdownId)));
        return ResponseHandler.generateOkResponse(dropdown);
    }
}
