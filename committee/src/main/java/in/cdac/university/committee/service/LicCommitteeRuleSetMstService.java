package in.cdac.university.committee.service;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.committee.bean.ComboBean;

import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.bean.LicCommitteeRuleSetDtlBean;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.repository.LicCommitteeRuleSetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetMstRespository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.Constants;
import in.cdac.university.committee.util.Language;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.RestUtility;
import in.cdac.university.committee.util.ServiceResponse;
import in.cdac.university.committee.util.StringUtils;

import in.cdac.university.committee.exception.*;


@Service
public class LicCommitteeRuleSetMstService {

    @Autowired
    private LicCommitteeRuleSetMstRespository licCommitteeRuleSetMstRespository;

    @Autowired
    private Language language;

    @Autowired
    private LicCommitteeRuleSetDtlRespository licCommitteeRuleSetDtlRespository;

    @Autowired
    private RestUtility restUtility;

    @Transactional
    public ServiceResponse saveLicCommitteeRule(LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) throws Exception {

        List<GbltLicCommitteeRuleSetMst> licCommitteeRuleSetMst = licCommitteeRuleSetMstRespository.findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumUnivId(
                List.of(1), licCommitteeRuleSetBeanMst.getUstrComRsName(), RequestUtility.getUniversityId());

        if (!licCommitteeRuleSetMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("LIC Committee Rule Set", licCommitteeRuleSetBeanMst.getUstrComRsName()));
        }
        GbltLicCommitteeRuleSetMst gbltLicCommitteeRuleSetMst = new GbltLicCommitteeRuleSetMst();
        BeanUtils.copyProperties(licCommitteeRuleSetBeanMst, gbltLicCommitteeRuleSetMst);

        gbltLicCommitteeRuleSetMst.setUnumComRsId(licCommitteeRuleSetMstRespository.getNextId());

        licCommitteeRuleSetMstRespository.save(gbltLicCommitteeRuleSetMst);


        List<LicCommitteeRuleSetDtlBean> committeeRuleList = licCommitteeRuleSetBeanMst.getCommitteeRuleList();
        if (!committeeRuleList.isEmpty()) {
            int count = 1;
            for (LicCommitteeRuleSetDtlBean licCommitteeRuleSetDtl : committeeRuleList) {
                licCommitteeRuleSetDtl.setUnumComRsId(gbltLicCommitteeRuleSetMst.getUnumComRsId());
                licCommitteeRuleSetDtl.setUnumComRsDtlId(Long.parseLong(
                        licCommitteeRuleSetDtl.getUnumComRsId() + StringUtils.padLeftZeros(count++ + "", 5)));
                licCommitteeRuleSetDtl.setUnumIsValid(1);
                licCommitteeRuleSetDtl.setUnumEntryUid(RequestUtility.getUserId());
                licCommitteeRuleSetDtl.setUnumUnivId(RequestUtility.getUniversityId());
                licCommitteeRuleSetDtl.setUdtEntryDate(new Date());
            }
            List<GbltLicCommitteeRuleSetDtl> gbltLicCommitteeRuleSetDtl = BeanUtils.copyListProperties(committeeRuleList, GbltLicCommitteeRuleSetDtl.class);
            licCommitteeRuleSetDtlRespository.saveAll(gbltLicCommitteeRuleSetDtl);
        }

        return ServiceResponse.builder().status(1).message(language.saveSuccess("LIC Committee Rule Set Save")).build();

    }

    @Transactional
    public ServiceResponse updateLicCommitteeRule(LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) {

        Integer universityId = RequestUtility.getUniversityId();
        List<GbltLicCommitteeRuleSetMst> licRuleSet = licCommitteeRuleSetMstRespository.findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumComRsIdNotAndUnumUnivId(
                List.of(1, 2), licCommitteeRuleSetBeanMst.getUstrComRsName(), licCommitteeRuleSetBeanMst.getUnumComRsId(), universityId);

        if (!licRuleSet.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("LIC Rule Set", licCommitteeRuleSetBeanMst.getUstrComRsName()));
        }

        // Create Log
        Integer noOfRecordsAffected = licCommitteeRuleSetMstRespository.createLog(licCommitteeRuleSetBeanMst.getUnumComRsId());
        licCommitteeRuleSetBeanMst.setUnumIsValid(1);
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("LIC Rule Set", licCommitteeRuleSetBeanMst.getUnumComRsId()));
        }
        GbltLicCommitteeRuleSetMst gbltLicCommitteeRuleSetMst = BeanUtils.copyProperties(licCommitteeRuleSetBeanMst, GbltLicCommitteeRuleSetMst.class);
        gbltLicCommitteeRuleSetMst.setUnumIsValid(1);
        licCommitteeRuleSetMstRespository.saveAndFlush(gbltLicCommitteeRuleSetMst);

        List<LicCommitteeRuleSetDtlBean> committeeRuleList = licCommitteeRuleSetBeanMst.getCommitteeRuleList();
        if (!committeeRuleList.isEmpty()) {
            // get next order no
            List<GbltLicCommitteeRuleSetDtl> existingRuleSetDetails = licCommitteeRuleSetDtlRespository.findByUnumComRsIdAndUnumUnivId(licCommitteeRuleSetBeanMst.getUnumComRsId(), universityId);

            AtomicInteger count = new AtomicInteger(existingRuleSetDetails.size());
            committeeRuleList.forEach(dtlBean -> {
                dtlBean.setUnumComRsId(licCommitteeRuleSetBeanMst.getUnumComRsId());
                dtlBean.setUnumComRsDtlId(Long.parseLong(
                        licCommitteeRuleSetBeanMst.getUnumComRsId() + StringUtils.padLeftZeros(count.incrementAndGet() + "", 5)));
                dtlBean.setUnumIsValid(1);
                dtlBean.setUnumUnivId(universityId);
                dtlBean.setUnumEntryUid(RequestUtility.getUserId());
                dtlBean.setUdtEntryDate(new Date());
            });
            licCommitteeRuleSetDtlRespository.createLog(licCommitteeRuleSetBeanMst.getUnumComRsId());
            List<GbltLicCommitteeRuleSetDtl> gbltLicCommitteeRuleSetDtl = BeanUtils.copyListProperties(committeeRuleList, GbltLicCommitteeRuleSetDtl.class);
            licCommitteeRuleSetDtlRespository.saveAll(gbltLicCommitteeRuleSetDtl);
        }

        return ServiceResponse.builder().status(1).message(language.saveSuccess("LIC Committee Rule Set Save")).build();

    }

    public List<LicCommitteeRuleSetBeanMst> getCommitteeCombo() {

        return BeanUtils.copyListProperties(
                licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValid(RequestUtility.getUniversityId(), 1), LicCommitteeRuleSetBeanMst.class);

    }

    public String getComboCourseTypeByRsID(Long unumComRsId) {
        LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst = BeanUtils.copyProperties(
                licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValidAndUnumComRsId(RequestUtility.getUniversityId(), 1, unumComRsId), LicCommitteeRuleSetBeanMst.class);
        String cCourseTypeName = null;
        if (licCommitteeRuleSetBeanMst != null) {
            String courseType = licCommitteeRuleSetBeanMst.getUstrCtypeids();
            if (!courseType.isEmpty()) {
                String[] courseTypes = courseType.split(",");
                ComboBean[] courseCombo = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_COURSE_TYPE, ComboBean[].class);
                if (courseCombo != null) {
                    Map<String, String> courseMap = Arrays.stream(courseCombo).collect(Collectors.toMap(ComboBean::getKey, ComboBean::getValue));
                    cCourseTypeName = Arrays.stream(courseTypes).map(e -> courseMap.getOrDefault(e, "")).collect(Collectors.joining(","));

                }
            }
        }
        return cCourseTypeName;
    }

    public List<LicCommitteeRuleSetBeanMst> getListPageData() {
        List<LicCommitteeRuleSetBeanMst> licCommitteeRuleSetBeanMst = BeanUtils.copyListProperties(
                licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValid(RequestUtility.getUniversityId(), 1), LicCommitteeRuleSetBeanMst.class);
        String cCourseTypeName = null;
        for (LicCommitteeRuleSetBeanMst licCommitteeRuleSet : licCommitteeRuleSetBeanMst) {
            String courseType = licCommitteeRuleSet.getUstrCtypeids();
            if (!courseType.isEmpty()) {
                String[] courseTypes = courseType.split(",");
                ComboBean[] courseCombo = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_COURSE_TYPE, ComboBean[].class);
                if (courseCombo != null) {
                    Map<String, String> courseMap = Arrays.stream(courseCombo).collect(Collectors.toMap(ComboBean::getKey, ComboBean::getValue));
                    cCourseTypeName = Arrays.stream(courseTypes).map(e -> courseMap.getOrDefault(e, "")).collect(Collectors.joining(","));
                }
            }
            licCommitteeRuleSet.setUstrCtypeids(cCourseTypeName);
        }

        return licCommitteeRuleSetBeanMst;

    }

    public ServiceResponse getCommitteeRuleSetByUnumComRsId(Long unumComRsId) throws Exception {

        LicCommitteeRuleSetBeanMst committeeMasterBean = new LicCommitteeRuleSetBeanMst();
        //unum_com_rs_id
        List<GbltLicCommitteeRuleSetMst> ruleSetMst = licCommitteeRuleSetMstRespository.getByUnumIsValidAndUnumUnivIdAndUnumComRsId(1, RequestUtility.getUniversityId(), unumComRsId);
        if (ruleSetMst.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", unumComRsId));
        BeanUtils.copyProperties(ruleSetMst.get(0), committeeMasterBean);

        List<GbltLicCommitteeRuleSetDtl> ruleSetDtl = licCommitteeRuleSetDtlRespository.findByUnumIsValidAndUnumUnivIdAndUnumComRsId(1, RequestUtility.getUniversityId(), unumComRsId); // and rsid=i

        if (!ruleSetDtl.isEmpty()) {
            List<LicCommitteeRuleSetDtlBean> committeeRulesetDtlBeanList = BeanUtils.copyListProperties(ruleSetDtl, LicCommitteeRuleSetDtlBean.class);
            committeeMasterBean.setCommitteeRuleList(committeeRulesetDtlBeanList);
        }

        return ServiceResponse.successObject(
                committeeMasterBean
        );
    }

}