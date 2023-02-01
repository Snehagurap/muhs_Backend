package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.CommitteeRulesetBean;
import in.cdac.university.committee.bean.CommitteeRulesetDtlBean;
import in.cdac.university.committee.entity.GbltCommitteeRulesetDtl;
import in.cdac.university.committee.entity.GbltCommitteeRulesetMst;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.CommitteeRulesetDtlRepository;
import in.cdac.university.committee.repository.CommitteeRulesetMstRepository;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommitteeRulesetService {

    @Autowired
    CommitteeRulesetMstRepository committeeRulesetMstRepository;

    @Autowired
    CommitteeRulesetDtlRepository committeeRulesetDtlRepository;

    @Autowired
    Language language;

    public List<CommitteeRulesetBean> getListPageData() {
        return BeanUtils.copyListProperties(
                committeeRulesetMstRepository.findByUnumUnivIdAndUnumIsvalid(RequestUtility.getUniversityId(), 1), CommitteeRulesetBean.class
        );
    }

    @Transactional
    public ServiceResponse saveCommitteeRuleset(CommitteeRulesetBean committeeRulesetBean) {
        if(committeeRulesetBean.getUnumNoOfMembers() != committeeRulesetBean.getCommitteeRulesetDtl().size()) {
            return ServiceResponse.errorResponse(language.message("Not all members details defined"));
        }

        // Check for duplicate
        List<GbltCommitteeRulesetMst> committeeRulesetMsts = committeeRulesetMstRepository.findByUstrComRsNameAndUnumIsvalidAndUnumUnivId(committeeRulesetBean.getUstrComRsName(), 1, committeeRulesetBean.getUnumUnivId());
        if (!committeeRulesetMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.message("Committee Ruleset already defined. Please modify the existing for any changes"));
        }

        GbltCommitteeRulesetMst gbltCommitteeRulesetMst = BeanUtils.copyProperties(committeeRulesetBean, GbltCommitteeRulesetMst.class);
        Long committeeRsID = committeeRulesetMstRepository.getNextId();
        gbltCommitteeRulesetMst.setUnumComRsId(committeeRsID);
        committeeRulesetMstRepository.save(gbltCommitteeRulesetMst);

        // Save Committee Ruleset Detail
        List<GbltCommitteeRulesetDtl> gbltCommitteeRulesetDtlList = new ArrayList<>();

        for (int index=1;index <= committeeRulesetBean.getCommitteeRulesetDtl().size();++index) {
            CommitteeRulesetDtlBean committeeRulesetDtlBean = committeeRulesetBean.getCommitteeRulesetDtl().get(index - 1);
            GbltCommitteeRulesetDtl gbltCommitteeRulesetDtl = BeanUtils.copyProperties(committeeRulesetDtlBean, GbltCommitteeRulesetDtl.class);

            Long committeeRsDtlId = Long.parseLong(committeeRsID + StringUtils.padLeftZeros(Integer.toString(index), 5));
            gbltCommitteeRulesetDtl.setUnumComRsDtlId(committeeRsDtlId);
            gbltCommitteeRulesetDtl.setUnumComRsId(committeeRsID);
            gbltCommitteeRulesetDtl.setUnumIsvalid(1);
            gbltCommitteeRulesetDtl.setUnumComrolsno(index);
            gbltCommitteeRulesetDtl.setUnumUnivId(committeeRulesetBean.getUnumUnivId());
            gbltCommitteeRulesetDtl.setUnumEntryUid(committeeRulesetBean.getUnumEntryUid());
            gbltCommitteeRulesetDtl.setUdtEntryDate(new Date());
            gbltCommitteeRulesetDtl.setUdtEffFrom(new Date());
            gbltCommitteeRulesetDtlList.add(gbltCommitteeRulesetDtl);
        }

        committeeRulesetDtlRepository.saveAll(gbltCommitteeRulesetDtlList);

        return ServiceResponse.successMessage(language.saveSuccess("Committee Ruleset"));
    }

    public ServiceResponse getCommitteeRulesetById(Long committeeRulesetId) {
        if(committeeRulesetId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Committee Ruleset"));
        }

        Optional<GbltCommitteeRulesetMst> gbltCommitteeRulesetMst = committeeRulesetMstRepository.findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(
                committeeRulesetId,1, RequestUtility.getUniversityId()
        );

        if(gbltCommitteeRulesetMst.isEmpty()){
            return ServiceResponse.errorResponse(language.notFoundForId("Committee Ruleset", committeeRulesetId));
        }

        CommitteeRulesetBean committeeRulesetBean = BeanUtils.copyProperties(gbltCommitteeRulesetMst.get(), CommitteeRulesetBean.class);

        List<GbltCommitteeRulesetDtl> gbltCommitteeRulesetDtlList = committeeRulesetDtlRepository.findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(
                committeeRulesetId, 1, RequestUtility.getUniversityId()
        );

        if(!gbltCommitteeRulesetDtlList.isEmpty()) {
            List<CommitteeRulesetDtlBean> committeeRulesetDtlBeanList = BeanUtils.copyListProperties(gbltCommitteeRulesetDtlList, CommitteeRulesetDtlBean.class);
            committeeRulesetBean.setCommitteeRulesetDtl(committeeRulesetDtlBeanList);
        }

        return ServiceResponse.successObject(committeeRulesetBean);
    }

    @Transactional
    public ServiceResponse updateCommitteeRuleset(CommitteeRulesetBean committeeRulesetBean) {
        if(committeeRulesetBean.getUnumComRsId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Committee Ruleset"));
        }

        List<Long> committeeRsId = List.of(committeeRulesetBean.getUnumComRsId());

        // Create Log committee ruleset mst
        Integer noOfAffectedRowsMst = committeeRulesetMstRepository.createLog(committeeRsId, RequestUtility.getUniversityId());
        if(noOfAffectedRowsMst == 0) {
            throw new ApplicationException(language.notFoundForId("Committee Ruleset", committeeRulesetBean.getUnumComRsId()));
        }
        // Create Log committee ruleset Dtl
        Integer noOfRowsAffectedDtl = committeeRulesetDtlRepository.createLog(committeeRsId, RequestUtility.getUniversityId());
        if(noOfRowsAffectedDtl == 0) {
            throw new ApplicationException(language.notFoundForId("Committee Ruleset", committeeRulesetBean.getUnumComRsId()));
        }

        //Save new Data committee ruleset mst
        GbltCommitteeRulesetMst gbltCommitteeRulesetMst = BeanUtils.copyProperties(committeeRulesetBean, GbltCommitteeRulesetMst.class);
        committeeRulesetMstRepository.save(gbltCommitteeRulesetMst);

        //Save new Data committee ruleset Dtl
        List<GbltCommitteeRulesetDtl> gbltCommitteeRulesetDtlList = new ArrayList<>();

        // Get count of existing ruleset details
        List<GbltCommitteeRulesetDtl> existingRules = committeeRulesetDtlRepository.findByUnumComRsIdAndUnumUnivId(committeeRulesetBean.getUnumComRsId(), committeeRulesetBean.getUnumUnivId());

        int count = existingRules.size();
        for (int index=1;index <= committeeRulesetBean.getCommitteeRulesetDtl().size();++index) {
            CommitteeRulesetDtlBean committeeRulesetDtlBean = committeeRulesetBean.getCommitteeRulesetDtl().get(index - 1);
            GbltCommitteeRulesetDtl gbltCommitteeRulesetDtl = BeanUtils.copyProperties(committeeRulesetDtlBean, GbltCommitteeRulesetDtl.class);

            Long committeeRsDtlId = Long.parseLong(committeeRulesetBean.getUnumComRsId() + StringUtils.padLeftZeros(Integer.toString(++count), 5));
            gbltCommitteeRulesetDtl.setUnumComRsDtlId(committeeRsDtlId);
            gbltCommitteeRulesetDtl.setUnumComRsId(committeeRulesetBean.getUnumComRsId());
            gbltCommitteeRulesetDtl.setUnumIsvalid(1);
            gbltCommitteeRulesetDtl.setUnumComrolsno(index);
            gbltCommitteeRulesetDtl.setUnumUnivId(committeeRulesetBean.getUnumUnivId());
            gbltCommitteeRulesetDtl.setUnumEntryUid(committeeRulesetBean.getUnumEntryUid());
            gbltCommitteeRulesetDtl.setUdtEntryDate(new Date());
            gbltCommitteeRulesetDtl.setUdtEffFrom(new Date());
            gbltCommitteeRulesetDtlList.add(gbltCommitteeRulesetDtl);
        }

        if(!gbltCommitteeRulesetDtlList.isEmpty())
            committeeRulesetDtlRepository.saveAll(gbltCommitteeRulesetDtlList);


        return ServiceResponse.successMessage(language.updateSuccess("Committee Ruleset"));
    }

    public List<CommitteeRulesetBean> getCommitteeRulesetCombo() {
        return BeanUtils.copyListProperties(
                committeeRulesetMstRepository.findByUnumUnivIdAndUnumIsvalid(RequestUtility.getUniversityId(), 1),
                CommitteeRulesetBean.class
        );
    }
}
