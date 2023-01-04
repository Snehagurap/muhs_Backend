package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.*;
import in.cdac.university.committee.entity.GbltCommitteeRulesetDtl;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.CommitteeRulesetDtlRepository;
import in.cdac.university.committee.repository.ScrutinycommitteeMemberDtlRepository;
import in.cdac.university.committee.repository.ScrutinycommitteeMstRepository;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScrutinycommitteeService {

    @Autowired
    ScrutinycommitteeMstRepository scrutinycommitteeMstRepository;

    @Autowired
    ScrutinycommitteeMemberDtlRepository scrutinycommitteeMemberDtlRepository;

    @Autowired
    CommitteeRulesetDtlRepository committeeRulesetDtlRepository;

    @Autowired
    Language language;

    @Autowired
    RestUtility restUtility;

    public ServiceResponse getComRsDataForScrutinyComCreation(Long committeeRsId, Integer facultyId) {
        if(committeeRsId == null || facultyId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Committee Ruleset Id & Faculty Id"));
        }

        List<GbltCommitteeRulesetDtl> gbltCommitteeRulesetDtlList = committeeRulesetDtlRepository.findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(
                committeeRsId, 1, RequestUtility.getUniversityId()
        );

        if(gbltCommitteeRulesetDtlList.isEmpty()) {
            throw new ApplicationException(language.message("Committee Ruleset not found"));
        }

        // Get All Teachers
        EmployeeBean[] teachers = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHERS, EmployeeBean[].class);
        if (teachers == null) {
            return ServiceResponse.errorResponse("Unable to get Teachers data");
        }

        // Get Teacher profiles
        EmployeeProfileBean[] teacherProfiles = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_PROFILE,  EmployeeProfileBean[].class);
        if (teacherProfiles == null) {
            return ServiceResponse.errorResponse("Unable to get Teacher Profiles");
        }

        Map<Long, Integer> facultyWiseTeachers = Arrays.stream(teacherProfiles)
                .filter(employeeProfileBean -> employeeProfileBean.getUnumFacultyId()!=null)
                .collect(Collectors.toMap(EmployeeProfileBean::getUnumEmpId, EmployeeProfileBean::getUnumFacultyId, (e1,e2) -> e2));

        // Get Teachers current Details
        EmployeeCurrentDetailBean[] teacherCurrentDetails = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_CURRENT_DETAILS,  EmployeeCurrentDetailBean[].class);
        if (teacherCurrentDetails == null) {
            return ServiceResponse.errorResponse("Unable to get Teachers Current Details");
        }

        Map<Long, Integer> DesignationWiseTeachers = Arrays.stream(teacherCurrentDetails)
                .filter(employeeCurrentDetailBean -> employeeCurrentDetailBean.getUnumEmpDesigid()!=null)
                .collect(Collectors.toMap(EmployeeCurrentDetailBean::getUnumEmpId, EmployeeCurrentDetailBean::getUnumEmpDesigid));

        List<EmployeeBean> teacherList = Arrays.stream(teachers).map(employeeBean -> {
            employeeBean.setUnumEmpDesigid(DesignationWiseTeachers.getOrDefault(employeeBean.getUnumEmpId(),null));
            employeeBean.setUnumFacultyId(facultyWiseTeachers.getOrDefault(employeeBean.getUnumEmpId(), null));
            return employeeBean;
        }).toList();

        List<CommitteeRulesetDtlBean> committeeRulesetDtlBeanList =gbltCommitteeRulesetDtlList.stream().map(committerulesetDtl -> {
            CommitteeRulesetDtlBean committeeRulesetDtlBean = BeanUtils.copyProperties(committerulesetDtl, CommitteeRulesetDtlBean.class);
            Integer comRsFacultyId = committerulesetDtl.getUnumRoleCfacultyId();
            if(comRsFacultyId != 0){
                teacherList.stream().filter(employeeBean ->  comRsFacultyId == 1 ? employeeBean.getUnumFacultyId()==facultyId : employeeBean.getUnumFacultyId() != facultyId
                ).collect(Collectors.toList());
            }
            if(committerulesetDtl.getUnumRoleDepartmentId() != 0) {
                teacherList.stream().filter(employeeBean -> employeeBean.getUnumDeptId()!=null && Objects.equals(employeeBean.getUnumDeptId(), committerulesetDtl.getUnumRoleDepartmentId())
                ).toList();
            }
            if(committerulesetDtl.getUnumRolePostId() != 0) {
                teacherList.stream().filter(employeeBean -> employeeBean.getUnumEmpDesigid()!=null && employeeBean.getUnumEmpDesigid() == committerulesetDtl.getUnumRolePostId()
                ).toList();
            }

            List<ComboBean> teachersCombo = teacherList.stream().map(employeeBean ->  new ComboBean(employeeBean.getUstrEmpId(), employeeBean.getUstrEmpName())).toList();

            committeeRulesetDtlBean.setTeacherCombo(teachersCombo);
            return committeeRulesetDtlBean;
        }).toList();

        return ServiceResponse.successObject(committeeRulesetDtlBeanList);
    }
}

