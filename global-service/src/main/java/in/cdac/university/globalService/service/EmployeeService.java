package in.cdac.university.globalService.service;



import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.GmstEmpCurDtl;
import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.entity.GmstEmpProfileDtl;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.EmployeeCurrentDetailRepository;
import in.cdac.university.globalService.repository.EmployeeProfileRepository;
import in.cdac.university.globalService.repository.EmployeeRepository;
import in.cdac.university.globalService.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    private EmployeeCurrentDetailRepository employeeCurrentDetailRepository;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private Language language;

    public List<EmployeeBean> listPageData(int status) throws Exception {
        return BeanUtils.copyListProperties(
               employeeRepository.listPageData(status, RequestUtility.getUniversityId()),
               EmployeeBean.class
        );
    }

    public List<EmployeeBean> getAllTeachers() throws Exception {
        return BeanUtils.copyListProperties(
                employeeRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId()),
                EmployeeBean.class
        );
    }

    public List<EmployeeBean> getCommitteeMembers(Long eventId) throws Exception {
        if (eventId == null)
            return new ArrayList<>();

        EventBean eventBean = restUtility.get(RestUtility.SERVICE_TYPE.COMMITTEE, Constants.URL_GET_EVENT + eventId, EventBean.class);
        if (eventBean == null) {
            return new ArrayList<>();
        }

        restUtility.get(RestUtility.SERVICE_TYPE.COMMITTEE, Constants.URL_GET_COMMITTEE_BY_ID + eventBean.getUnumComid(), CommitteeBean.class);

        return BeanUtils.copyListProperties(
                employeeRepository.listPageData(1, RequestUtility.getUniversityId()),
                EmployeeBean.class
        );
    }

    @Transactional
    public ServiceResponse save(EmployeeBean employeeBean) {
        GmstEmpMst gmstEmpMst = BeanUtils.copyProperties(employeeBean, GmstEmpMst.class);
        Long empID = employeeRepository.getNextId();
        gmstEmpMst.setUnumEmpId(empID);

        List<GmstEmpProfileDtl> gmstEmpProfileDtlList = new ArrayList<>();
        List<EmployeeProfileBean> employeeProfileBeanList = employeeBean.getEmployeeProfileList();
        if(employeeProfileBeanList != null && employeeProfileBeanList.size()>0){
            Long maxEmpProfileId = employeeProfileRepository.getMaxEmpProfileId(empID);
            for(EmployeeProfileBean employeeProfileBean :employeeProfileBeanList) {
                GmstEmpProfileDtl gmstEmpProfileDtl = BeanUtils.copyProperties(employeeProfileBean, GmstEmpProfileDtl.class);
                gmstEmpProfileDtl.setUnumEmpId(empID);
                maxEmpProfileId++;
                gmstEmpProfileDtl.setUnumProfileId(Long.valueOf(empID.toString() + "" + StringUtility.padLeftZeros(maxEmpProfileId.toString(), 5)));
                gmstEmpProfileDtl.setUnumCollegeId(employeeBean.getUnumCollegeId());
                gmstEmpProfileDtl.setUnumIsvalid(1);
                gmstEmpProfileDtl.setUdtEntryDate(employeeBean.getUdtEntryDate());
                gmstEmpProfileDtl.setUnumUnivId(employeeBean.getUnumUnivId());
                gmstEmpProfileDtl.setUnumEntryUid(employeeBean.getUnumEntryUid());
                gmstEmpProfileDtl.setUdtEffFrom(employeeBean.getUdtEffFrom());
                gmstEmpProfileDtlList.add(gmstEmpProfileDtl);
            }

            employeeProfileRepository.saveAll(gmstEmpProfileDtlList);
        }


        GmstEmpCurDtl gmstEmpCurDtl = new GmstEmpCurDtl();
        gmstEmpCurDtl.setUnumEmpId(empID);
        gmstEmpCurDtl.setUnumEmpDesigid(employeeBean.getUnumEmpDesigid());
        gmstEmpCurDtl.setUstrTAadharNo(employeeBean.getUstrTAadharNo());
        gmstEmpCurDtl.setUdtUgJoiningDate(employeeBean.getUdtUgJoiningDate());
        gmstEmpCurDtl.setUdtPgJoiningDate(employeeBean.getUdtPgJoiningDate());
        gmstEmpCurDtl.setUnumIsvalid(1);
        gmstEmpCurDtl.setUdtEntryDate(employeeBean.getUdtEntryDate());
        gmstEmpCurDtl.setUnumUnivId(employeeBean.getUnumUnivId());
        gmstEmpCurDtl.setUnumEntryUid(employeeBean.getUnumEntryUid());


        Long maxEmpCurrDetailId = employeeCurrentDetailRepository.getMaxEmpCurrDetailId(empID);
        maxEmpCurrDetailId++;
        gmstEmpCurDtl.setUnumEmpCurId(Long.valueOf(empID.toString() + "" + StringUtility.padLeftZeros(maxEmpCurrDetailId.toString(), 5)));

        employeeCurrentDetailRepository.save(gmstEmpCurDtl);


        employeeRepository.save(gmstEmpMst);
        return ServiceResponse.successMessage(language.saveSuccess("Teacher detail"));
    }

    public ServiceResponse getTeacherById(Long teacherId) throws Exception {
        Optional<GmstEmpMst> gmstEmpMstOptional = employeeRepository.findByUnumEmpIdAndUnumIsvalidInAndUnumUnivId(
                teacherId, List.of(1, 2), RequestUtility.getUniversityId()
        );

        if (gmstEmpMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Teacher", teacherId));

        EmployeeBean employeeBean = BeanUtils.copyProperties(gmstEmpMstOptional.get(), EmployeeBean.class);

        Optional<GmstEmpCurDtl> gmstEmpCurDtlOptional = employeeCurrentDetailRepository.findByUnumIsvalidAndUnumEmpId(1, teacherId);

        if (gmstEmpCurDtlOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Current Detail", teacherId));

        EmployeeCurrentDetailBean employeeCurrentDetail = BeanUtils.copyProperties(gmstEmpCurDtlOptional.get(), EmployeeCurrentDetailBean.class);

        employeeBean.setUstrTAadharNo(employeeCurrentDetail.getUstrTAadharNo());
        employeeBean.setUnumEmpDesigid(employeeCurrentDetail.getUnumEmpDesigid());
        employeeBean.setUdtUgJoiningDate(employeeCurrentDetail.getUdtUgJoiningDate());
        employeeBean.setUdtPgJoiningDate(employeeCurrentDetail.getUdtPgJoiningDate());

        List<GmstEmpProfileDtl> gmstEmpProfileDtlList = employeeProfileRepository.findByUnumIsvalidAndUnumEmpId(1, teacherId);

        if(gmstEmpProfileDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Profile Detail", teacherId));
        }

        List<EmployeeProfileBean> employeeProfileList = BeanUtils.copyListProperties(gmstEmpProfileDtlList, EmployeeProfileBean.class);
        
        employeeBean.setEmployeeProfileList(employeeProfileList);
        employeeBean.setUnumCollegeId(employeeProfileList.get(0).getUnumCollegeId());
        return ServiceResponse.builder()
                .status(1)
                .responseObject(employeeBean)
                .build();
    }

    @Transactional
    public ServiceResponse update(EmployeeBean employeeBean) {
        if(employeeBean.getUnumEmpId() == null){
            return ServiceResponse.errorResponse(language.mandatory("Teacher Id"));
        }

        // Create Log
        int noOfRecordsAffected = employeeRepository.createLog(List.of(employeeBean.getUnumEmpId()));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Teacher", employeeBean.getUnumEmpId()));
        }

        int noOfRowsAffectedCurDtl = employeeCurrentDetailRepository.createLog(List.of(employeeBean.getUnumEmpId()));
        if(noOfRowsAffectedCurDtl == 0) {
            throw new ApplicationException(language.updateError("Teacher"));
        }

        int noOfRowsAffectedProfile = employeeProfileRepository.createLog(List.of(employeeBean.getUnumEmpId()));
        if(noOfRowsAffectedProfile != employeeBean.getEmployeeProfileList().size()) {
            throw new ApplicationException(language.updateError("Teacher"));
        }


        // save new Record
        GmstEmpMst empMst = BeanUtils.copyProperties(employeeBean, GmstEmpMst.class);
        Long empID = employeeRepository.getNextId();
        empMst.setUnumEmpId(empID);
        employeeRepository.save(empMst);


        List<GmstEmpProfileDtl> gmstEmpProfileDtlList = new ArrayList<>();
        List<EmployeeProfileBean> employeeProfileBeanList = employeeBean.getEmployeeProfileList();
        if(employeeProfileBeanList != null && employeeProfileBeanList.size()>0){
            Long maxEmpProfileId = employeeProfileRepository.getMaxEmpProfileId(empID);
            for(EmployeeProfileBean employeeProfileBean :employeeProfileBeanList) {
                GmstEmpProfileDtl gmstEmpProfileDtl = BeanUtils.copyProperties(employeeProfileBean, GmstEmpProfileDtl.class);
                gmstEmpProfileDtl.setUnumEmpId(empID);
                maxEmpProfileId++;
                gmstEmpProfileDtl.setUnumProfileId(Long.valueOf(empID.toString() + "" + StringUtility.padLeftZeros(maxEmpProfileId.toString(), 5)));
                gmstEmpProfileDtl.setUnumCollegeId(employeeBean.getUnumCollegeId());
                gmstEmpProfileDtl.setUnumIsvalid(1);
                gmstEmpProfileDtl.setUdtEntryDate(employeeBean.getUdtEntryDate());
                gmstEmpProfileDtl.setUnumUnivId(employeeBean.getUnumUnivId());
                gmstEmpProfileDtl.setUnumEntryUid(employeeBean.getUnumEntryUid());
                gmstEmpProfileDtl.setUdtEffFrom(employeeBean.getUdtEffFrom());
                gmstEmpProfileDtlList.add(gmstEmpProfileDtl);
            }

            employeeProfileRepository.saveAll(gmstEmpProfileDtlList);
        }


        GmstEmpCurDtl gmstEmpCurDtl = new GmstEmpCurDtl();
        gmstEmpCurDtl.setUnumEmpId(empID);
        gmstEmpCurDtl.setUnumEmpDesigid(employeeBean.getUnumEmpDesigid());
        gmstEmpCurDtl.setUstrTAadharNo(employeeBean.getUstrTAadharNo());
        gmstEmpCurDtl.setUdtUgJoiningDate(employeeBean.getUdtUgJoiningDate());
        gmstEmpCurDtl.setUdtPgJoiningDate(employeeBean.getUdtPgJoiningDate());
        gmstEmpCurDtl.setUnumIsvalid(1);
        gmstEmpCurDtl.setUdtEntryDate(employeeBean.getUdtEntryDate());
        gmstEmpCurDtl.setUnumUnivId(employeeBean.getUnumUnivId());
        gmstEmpCurDtl.setUnumEntryUid(employeeBean.getUnumEntryUid());


        Long maxEmpCurrDetailId = employeeCurrentDetailRepository.getMaxEmpCurrDetailId(empID);
        maxEmpCurrDetailId++;
        gmstEmpCurDtl.setUnumEmpCurId(Long.valueOf(empID.toString() + "" + StringUtility.padLeftZeros(maxEmpCurrDetailId.toString(), 5)));

        employeeCurrentDetailRepository.save(gmstEmpCurDtl);



        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Teacher"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(EmployeeBean employeeBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Teacher Id"));
        }

        List<GmstEmpMst> empMstList = employeeRepository.findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete), List.of(1, 2), employeeBean.getUnumUnivId()
        );

        if(empMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Teacher", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = employeeRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Teacher"));
        }

        empMstList.forEach(teacher -> {
            teacher.setUnumIsvalid(0);
            teacher.setUdtEntryDate(employeeBean.getUdtEntryDate());
            teacher.setUnumEntryUid(employeeBean.getUnumEntryUid());
        });

        employeeRepository.saveAll(empMstList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Teacher"))
                .build();
    }

    @Transactional
    public ServiceResponse updateChairmanFlag(List<Long> employeesToFlag) {
        employeeRepository.updateChairmanFlag(employeesToFlag);
        return ServiceResponse.successMessage(language.message("Flag updated successfully"));
    }

    @Transactional
    public ServiceResponse updateMember1Flag(List<Long> employeesToFlag) {
        employeeRepository.updateMember1Flag(employeesToFlag);
        return ServiceResponse.successMessage(language.message("Flag updated successfully"));
    }

    @Transactional
    public ServiceResponse updateMember2Flag(List<Long> employeesToFlag) {
        employeeRepository.updateMember2Flag(employeesToFlag);
        return ServiceResponse.successMessage(language.message("Flag updated successfully"));
    }

    @Transactional
    public ServiceResponse updateCommitteeSelectionFlag(List<Long> employeesToFlag) {
        employeeRepository.updateCommitteeSelectionFlag(employeesToFlag);
        return ServiceResponse.successMessage(language.message("Flag updated successfully"));
    }
}
