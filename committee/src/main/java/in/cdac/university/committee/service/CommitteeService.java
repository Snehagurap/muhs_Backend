package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.CommitteeBean;
import in.cdac.university.committee.bean.CommitteeDetailBean;
import in.cdac.university.committee.entity.GbltCommitteeDtl;
import in.cdac.university.committee.entity.GbltCommitteeMst;
import in.cdac.university.committee.repository.CommitteeDetailRepository;
import in.cdac.university.committee.repository.CommitteeMasterRepository;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommitteeService {

    @Autowired
    private CommitteeMasterRepository committeeMasterRepository;

    @Autowired
    private CommitteeDetailRepository committeeDetailRepository;

    @Autowired
    private Language language;

    public ServiceResponse createCommittee(CommitteeBean committeeBean) {
        int noOfMembers = committeeBean.getUnumNoOfMembers();
        if (noOfMembers != committeeBean.getCommitteeDetail().size()) {
            return ServiceResponse.errorResponse(language.message("Not all members details defined"));
        }

        GbltCommitteeMst committeeMst = BeanUtils.copyProperties(committeeBean, GbltCommitteeMst.class);
        // Generate Primary Key
        Long committeeId = committeeMasterRepository.getNextId();
        committeeMst.setUnumComid(committeeId);
        committeeMasterRepository.save(committeeMst);

        // Save Committee Master Detail

        List<GbltCommitteeDtl> committeeDtlList = new ArrayList<>();

        for (int index=1;index <= committeeBean.getCommitteeDetail().size();++index) {
            CommitteeDetailBean committeeDetailBean = committeeBean.getCommitteeDetail().get(index - 1);
            GbltCommitteeDtl committeeDtl = BeanUtils.copyProperties(committeeDetailBean, GbltCommitteeDtl.class);

            Long committeeRoleId = Long.parseLong(committeeId + StringUtils.padLeftZeros(Integer.toString(index), 5));
            committeeDtl.setUnumComroleid(committeeRoleId);
            committeeDtl.setUnumComid(committeeId);
            committeeDtl.setUnumIsvalid(1);
            committeeDtl.setUnumComrolsno(index);
            committeeDtl.setUnumUnivId(committeeBean.getUnumUnivId());
            committeeDtl.setUnumEntryUid(committeeBean.getUnumEntryUid());
            committeeDtlList.add(committeeDtl);
        }

        committeeDetailRepository.saveAll(committeeDtlList);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Committee Details"))
                .build();
    }

    public List<CommitteeBean> getCommitteeList() throws Exception {
        Integer universityId = RequestUtility.getUniversityId();

        return BeanUtils.copyListProperties(
                committeeMasterRepository.findByUnumUnivIdAndUnumIsvalidOrderByUstrComNameAsc(universityId, 1),
                CommitteeBean.class
        );
    }
}
