package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.service.StateService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usm/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping("/list/{countryCode}")
    public @ResponseBody ResponseEntity<?> getList(@PathVariable("countryCode") Integer countryCode) throws IllegalAccessException {
        List<ComboBean> comboBeans = new ArrayList<>();
        comboBeans.add(new ComboBean("", "Select Value"));
        comboBeans.addAll(ComboUtility.generateComboData(stateService.getAllStates(countryCode)));
        return ResponseHandler.generateOkResponse(comboBeans);
    }

    @GetMapping("stateCombo")
    public @ResponseBody ResponseEntity<?> getStateByStateCode() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(stateService.getStateByStateCode())
        );
    }
}
