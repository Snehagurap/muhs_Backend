package in.cdac.university.committee.controller;

import in.cdac.university.committee.bean.CommitteeRoleBean;
import in.cdac.university.committee.service.CommitteeRoleService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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

    @GetMapping("{roleId}")
    public ResponseEntity<?> getCommitteeRole(@PathVariable("roleId") Integer roleId) {
        return ResponseHandler.generateResponse(
                committeeRoleService.getCommitteeRole(roleId)
        );
    }

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") int status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        committeeRoleService.listPageData(status)
                )
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveCommitteeRoleDtl(@Valid @RequestBody CommitteeRoleBean committeeRoleBean) {
        committeeRoleBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeRoleBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(committeeRoleService.save(committeeRoleBean));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody CommitteeRoleBean committeeRoleBean) {
        committeeRoleBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeRoleBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                committeeRoleService.update(committeeRoleBean)
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Integer[] idsToDelete) {
        CommitteeRoleBean committeeRoleBean = new CommitteeRoleBean();
        committeeRoleBean.setUdtEntryDate(new Date());
        committeeRoleBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                committeeRoleService.delete(committeeRoleBean, idsToDelete)
        );
    }
}
