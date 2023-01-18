package in.cdac.university.committee.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.committee.bean.ComboBean;
import in.cdac.university.committee.bean.EmployeeBean;
import in.cdac.university.committee.bean.EmployeeCurrentDetailBean;
import in.cdac.university.committee.bean.EmployeeProfileBean;
import in.cdac.university.committee.bean.LicCommitteeBean;
import in.cdac.university.committee.bean.LicCommitteeDtlBean;
import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.bean.LicCommitteeRuleSetDtlBean;
import in.cdac.university.committee.entity.GbltLicCommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtl;
import in.cdac.university.committee.entity.GmstCommitteeRoleMst;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.CommitteeRoleRepository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetMstRespository;
import in.cdac.university.committee.repository.LicCommitteetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteetMstRespository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.Constants;
import in.cdac.university.committee.util.Language;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.RestUtility;
import in.cdac.university.committee.util.ServiceResponse;

@Service
public class LicCommitteeMstService {

	@Autowired
    private Language language;
    
    @Autowired
    private LicCommitteetMstRespository licCommitteetMstRespository;
    
    @Autowired
    private LicCommitteetDtlRespository licCommitteetDtlRespository;
    
    @Autowired
    private LicCommitteeRuleSetMstRespository licCommitteeRuleSetMstRespository;
    
    @Autowired
    private LicCommitteeRuleSetDtlRespository licCommitteeRuleSetDtlRespository;
    
    @Autowired
    CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    RestUtility restUtility;
    
    @Transactional
	public ServiceResponse saveLicCommittee(LicCommitteeBean licCommitteeBean) throws Exception{
		
    	List<GbltLicCommitteeMst> licCommitteeMst = licCommitteetMstRespository.findByUnumIsValidInAndUstrLicNameIgnoreCaseAndUnumUnivId(
                List.of(1), licCommitteeBean.getUstrLicName(),licCommitteeBean.getUnumUnivId());
        if (!licCommitteeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee", licCommitteeBean.getUstrLicName()));
        }
        GbltLicCommitteeMst gbltLicCommitteeMst = new GbltLicCommitteeMst();
        BeanUtils.copyProperties(licCommitteeBean, gbltLicCommitteeMst);
        gbltLicCommitteeMst.setUnumLicId(licCommitteetMstRespository.getNextId());
        licCommitteetMstRespository.save(gbltLicCommitteeMst);
        List<LicCommitteeDtlBean> licCommitteeDtlBean = licCommitteeBean.getLicCommitteeDtlBean();
        if(!licCommitteeDtlBean.isEmpty()) {
        	for(LicCommitteeDtlBean licCommitteeDtl : licCommitteeDtlBean) {
        		licCommitteeDtl.setUnumLicMemberId(licCommitteetDtlRespository.getNextId());
        		licCommitteeDtl.setUnumLicRsId(licCommitteeBean.getUnumComRsId());
        		licCommitteeDtl.setUnumIsValid(1);
        		licCommitteeDtl.setUnumEntryUid(RequestUtility.getUserId());
        		licCommitteeDtl.setUnumUnivId(RequestUtility.getUniversityId());
        		licCommitteeDtl.setUdtEntryDate(new Date());
        	}
            List<GbltLicCommitteeMemberDtl> gbltLicCommitteeDtl = BeanUtils.copyListProperties(licCommitteeDtlBean, GbltLicCommitteeMemberDtl.class);
            licCommitteetDtlRespository.saveAll(gbltLicCommitteeDtl);
        }
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Lic Committee Save")).build();
    }

	public ServiceResponse getComRsDataForScrutinyComCreation(Long licCommitteeRsId, Integer facultyId) {
		if(licCommitteeRsId == null || facultyId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Lic Committee Ruleset Id & Faculty Id"));
        }
		
		List<GbltLicCommitteeRuleSetMst> gbltLicCommitteeRuleSetMst = licCommitteeRuleSetMstRespository.findByUnumComRsIdAndUnumIsValidAndUnumUnivId(
                licCommitteeRsId, 1, RequestUtility.getUniversityId()
        );
        if(gbltLicCommitteeRuleSetMst.isEmpty()) {
            throw new ApplicationException(language.message("Lic Committee Ruleset not found"));
        }

        List<GbltLicCommitteeRuleSetDtl> gbltLicCommitteeRuleSetDtl = licCommitteeRuleSetDtlRespository.findByUnumComRsIdAndUnumIsValidAndUnumUnivId(
                licCommitteeRsId, 1, RequestUtility.getUniversityId()
        );

        if(gbltLicCommitteeRuleSetDtl.isEmpty()) {
            throw new ApplicationException(language.message("Lic Committee Ruleset Details not found"));
        }
        
        Map<Integer, String> committeeRoleMapList = committeeRoleRepository.getAllCommitteeRoles(RequestUtility.getUniversityId()).stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId,GmstCommitteeRoleMst::getUstrRoleFname));
        
        // Get All Teachers
        List<EmployeeBean> teachers = List.of(restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHERS, EmployeeBean[].class));
        if (teachers.isEmpty()) {
            return ServiceResponse.errorResponse("Unable to get Teachers data");
        }
        
        // Get Teacher profiles
        EmployeeProfileBean[] teacherProfiles = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_PROFILE,  EmployeeProfileBean[].class);
        if (teacherProfiles == null) {
            return ServiceResponse.errorResponse("Unable to get Teacher Profiles");
        }
        
        Map<Long, Set<Integer>> facultyWiseTeachers = Arrays.stream(teacherProfiles).filter(employeeProfileBean -> employeeProfileBean.getUnumFacultyId()!=null)
                .collect(Collectors.toMap(EmployeeProfileBean::getUnumEmpId, EmployeeProfileBean -> {
                        Set<Integer> ids = new HashSet<>();
                        ids.add(EmployeeProfileBean.getUnumFacultyId());
                        return ids;
                }, (e1, e2) -> {
                        e1.addAll(e2);
                        return e1;

                }));
        
        // Get Teachers current Details
        EmployeeCurrentDetailBean[] teacherCurrentDetails = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_CURRENT_DETAILS,  EmployeeCurrentDetailBean[].class);
        if (teacherCurrentDetails == null) {
            return ServiceResponse.errorResponse("Unable to get Teachers Current Details");
        }
        
        Map<Long, Integer> designationWiseTeachers = Arrays.stream(teacherCurrentDetails)
                .filter(employeeCurrentDetailBean -> employeeCurrentDetailBean.getUnumEmpDesigid()!=null)
                .collect(Collectors.toMap(EmployeeCurrentDetailBean::getUnumEmpId, EmployeeCurrentDetailBean::getUnumEmpDesigid));

        List<LicCommitteeRuleSetDtlBean> licCommitteeRulesetDtlBeanList = gbltLicCommitteeRuleSetDtl.stream().map(licCommitterulesetDtl -> {
            LicCommitteeRuleSetDtlBean licCommitteeRuleSetDtlBean = BeanUtils.copyProperties(licCommitterulesetDtl, LicCommitteeRuleSetDtlBean.class);
            licCommitteeRuleSetDtlBean.setUstrRoleName(committeeRoleMapList.getOrDefault(licCommitteeRuleSetDtlBean.getUnumRoleId(), ""));
            licCommitteeRuleSetDtlBean.setUnumNoOfMembers(gbltLicCommitteeRuleSetMst.get(0).getUnumNoOfMembers());
            int comRsFacultyId = licCommitterulesetDtl.getUnumRoleCfacultyId();
            List<EmployeeBean> finalTeacherList = teachers;

            if(comRsFacultyId != 0){
                finalTeacherList = finalTeacherList.stream().filter(employeeBean ->
                        facultyWiseTeachers.get(employeeBean.getUnumEmpId()) != null && (comRsFacultyId == 1 ? facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId) : !facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId))
                ).collect(Collectors.toList());
            }
            
            if(licCommitterulesetDtl.getUnumRoleDepartmentId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> employeeBean.getUnumDeptId()!=null && Objects.equals(employeeBean.getUnumDeptId(), licCommitterulesetDtl.getUnumRoleDepartmentId())
                ).toList();
            }
            if(licCommitterulesetDtl.getUnumRolePostId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> designationWiseTeachers.get(employeeBean.getUnumEmpId())!=null && designationWiseTeachers.containsValue(licCommitterulesetDtl.getUnumRolePostId())
                ).toList();
            }
            List<ComboBean> teachersCombo = finalTeacherList.stream().map(employeeBean ->  new ComboBean(employeeBean.getUnumEmpId().toString(), employeeBean.getUstrEmpName())).toList();
     
            
            licCommitteeRuleSetDtlBean.setTeacherCombo(teachersCombo);
            return licCommitteeRuleSetDtlBean;
        }).toList();
        
        return ServiceResponse.successObject(licCommitteeRulesetDtlBeanList);
	}
}
