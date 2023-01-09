package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.UserDatasetBean;
import in.cdac.university.usm.service.UserDatasetService;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usm/userDataset")
public class UserDatasetController {

    @Autowired
    private UserDatasetService userDatasetService;

    @GetMapping("combo/{userId}/{datasetId}")
    @ApiOperation(value="Get the list of dataset Mapped and Not Mapped to the User Id")
    public ResponseEntity<?> getUserDatasets(@PathVariable("userId") Long userId,
                                             @PathVariable("datasetId") Integer datasetId) throws IllegalAccessException {
        return ResponseHandler.generateResponse(
                userDatasetService.getDatasets(userId,datasetId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody UserDatasetBean userDatasetBean){
        return ResponseHandler.generateResponse(userDatasetService.save(userDatasetBean));
    }

}
