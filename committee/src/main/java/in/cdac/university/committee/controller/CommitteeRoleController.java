package in.cdac.university.committee.controller;

import in.cdac.university.committee.service.CommitteeRoleService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/committee/role")
public class CommitteeRoleController {

    @Autowired
    private CommitteeRoleService committeeRoleService;

    @GetMapping("combo")
    public ResponseEntity<?> getCommitteeRoleCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        committeeRoleService.getAllCommitteeRoles()
                )
        );
    }
}
