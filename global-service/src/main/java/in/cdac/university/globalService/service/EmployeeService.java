package in.cdac.university.globalService.service;


import in.cdac.university.globalService.bean.CommitteeBean;
import in.cdac.university.globalService.bean.EmployeeBean;
import in.cdac.university.globalService.bean.EventBean;
import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.EmployeeRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
        gmstEmpMst.setUnumEmpId(employeeRepository.getNextId());
        employeeRepository.save(gmstEmpMst);
        return ServiceResponse.successMessage(language.message("Teacher detail"));
    }

    public ServiceResponse getTeacherById(Long teacherId) throws Exception {
        Optional<GmstEmpMst> gmstEmpMstOptional = employeeRepository.findByUnumEmpIdAndUnumIsvalidInAndUnumUnivId(
                teacherId, List.of(1, 2), RequestUtility.getUniversityId()
        );

        if (gmstEmpMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Teacher", teacherId));

        EmployeeBean employeeBean = BeanUtils.copyProperties(gmstEmpMstOptional.get(), EmployeeBean.class);

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

        // save new Record
        GmstEmpMst empMst = BeanUtils.copyProperties(employeeBean, GmstEmpMst.class);
        employeeRepository.save(empMst);

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
}
