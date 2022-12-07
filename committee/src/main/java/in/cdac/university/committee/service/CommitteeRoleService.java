package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.CommitteeRoleBean;
import in.cdac.university.committee.entity.GmstCommitteeRoleMst;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.CommitteeRoleRepository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.Language;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CommitteeRoleService {

    @Autowired
    private CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    private Language language;

    public List<CommitteeRoleBean> getAllCommitteeRoles() {
        return BeanUtils.copyListProperties(
                committeeRoleRepository.getAllCommitteeRoles(
                        RequestUtility.getUniversityId()
                ),
                CommitteeRoleBean.class
        );
    }

    public ServiceResponse getCommitteeRole(Integer roleId) {
        Optional<GmstCommitteeRoleMst> committeeRoleMstOptional = committeeRoleRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumRoleId(
                List.of(1, 2), RequestUtility.getUniversityId(), roleId
        );

        if (committeeRoleMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee Role", roleId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(committeeRoleMstOptional.get(), CommitteeRoleBean.class))
                .build();
    }

    public List<CommitteeRoleBean> listPageData(int status) {
        return BeanUtils.copyListProperties(
                committeeRoleRepository.listPageData(status, RequestUtility.getUniversityId()),
                CommitteeRoleBean.class
        );
    }

    @Transactional
    public ServiceResponse save(CommitteeRoleBean committeeRoleBean) {
        //Duplicate check
        List<GmstCommitteeRoleMst> committeeRoleList = committeeRoleRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrRoleFnameIgnoreCase(
                List.of(1, 2), committeeRoleBean.getUnumUnivId(), committeeRoleBean.getUstrRoleFname()
        );
        if (!committeeRoleList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Committee Role", committeeRoleBean.getUstrRoleFname()));
        }
        GmstCommitteeRoleMst gmstCommitteeRoleMst = BeanUtils.copyProperties(committeeRoleBean, GmstCommitteeRoleMst.class);
        Integer roleId = committeeRoleRepository.getNextId();
        gmstCommitteeRoleMst.setUnumRoleId(roleId);
        committeeRoleRepository.save(gmstCommitteeRoleMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Committee Role"))
                .build();
    }

    @Transactional
    public ServiceResponse update(CommitteeRoleBean committeeRoleBean) {
        if (committeeRoleBean.getUnumRoleId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Role Id")));
        }

        //Duplicate Check
        List<GmstCommitteeRoleMst> committeeRoleMsts = committeeRoleRepository.findByUnumIsvalidInAndUstrRoleFnameIgnoreCaseAndUnumUnivIdAndUnumRoleIdNot(
                List.of(1, 2), committeeRoleBean.getUstrRoleFname(), committeeRoleBean.getUnumUnivId(), committeeRoleBean.getUnumRoleId()
        );

        if (!committeeRoleMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Committee Role", committeeRoleBean.getUstrRoleFname()));
        }

        // Create Log
        int noOfRecordsAffected = committeeRoleRepository.createLog(List.of((committeeRoleBean.getUnumRoleId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Committee Role", committeeRoleBean.getUnumRoleId()));
        }

        // Save new Data
        GmstCommitteeRoleMst committeeRoleMst = BeanUtils.copyProperties(committeeRoleBean, GmstCommitteeRoleMst.class);
        committeeRoleRepository.save(committeeRoleMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Committee Role"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(CommitteeRoleBean committeeRoleBean, Integer[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Role Id"));
        }

        List<GmstCommitteeRoleMst> committeeRoleMsts = committeeRoleRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumRoleIdIn(
                List.of(1, 2), committeeRoleBean.getUnumUnivId(), List.of(idsToDelete)
        );

        if (committeeRoleMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee Role", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = committeeRoleRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Committee Role"));
        }

        committeeRoleMsts.forEach(role -> {
            role.setUnumIsvalid(0);
            role.setUdtEntryDate(committeeRoleBean.getUdtEntryDate());
        });

        committeeRoleRepository.saveAll(committeeRoleMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Committee Role"))
                .build();
    }
}
