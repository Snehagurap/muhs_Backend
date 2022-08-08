package in.cdac.university.usm.controller;

import in.cdac.university.usm.service.CountryService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usm/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("list")
    public @ResponseBody ResponseEntity<?> getList() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(countryService.getAllCountries())
        );
    }
}
