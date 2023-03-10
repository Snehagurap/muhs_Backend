package in.cdac.university.committee.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import in.cdac.university.committee.bean.*;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.committee.entity.GbltLicCommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GmstCommitteeRoleMst;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.CommitteeRoleRepository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetMstRespository;
import in.cdac.university.committee.repository.LicCommitteetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteetMstRespository;

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
    private CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private PdfGeneratorUtil pdfGeneratorUtil;

    @Transactional
    public ServiceResponse saveLicCommittee(LicCommitteeBean licCommitteeBean) throws Exception {

        List<GbltLicCommitteeMst> licCommitteeMst = licCommitteetMstRespository.findByUnumIsValidInAndUstrLicNameIgnoreCaseAndUnumUnivId(
                List.of(1, 2), licCommitteeBean.getUstrLicName(), licCommitteeBean.getUnumUnivId());
        if (!licCommitteeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee", licCommitteeBean.getUstrLicName()));
        }
        int slNo = 1;
        Set<Long> members = new HashSet<>();
        for (LicCommitteeDtlBean licCommitteeDtlBean : licCommitteeBean.getLicCommitteeDtlBean()) {
            if (licCommitteeDtlBean.getUnumLicPref1Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref2Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref3Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref4Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref5Empid() == null) {
                return ServiceResponse.errorResponse("No employee selected for S.No." + slNo);
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref1Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref1Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref2Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref2Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref3Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref3Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref4Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref4Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref5Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref5Empid()));
            }
            slNo++;
        }
        GbltLicCommitteeMst gbltLicCommitteeMst = new GbltLicCommitteeMst();
        BeanUtils.copyProperties(licCommitteeBean, gbltLicCommitteeMst);
        gbltLicCommitteeMst.setUnumLicId(licCommitteetMstRespository.getNextId());
        licCommitteetMstRespository.save(gbltLicCommitteeMst);
        List<LicCommitteeDtlBean> licCommitteeDtlBean = licCommitteeBean.getLicCommitteeDtlBean();
        if (!licCommitteeDtlBean.isEmpty()) {
            for (LicCommitteeDtlBean licCommitteeDtl : licCommitteeDtlBean) {
                licCommitteeDtl.setUnumLicMemberId(licCommitteetDtlRespository.getNextId());
                licCommitteeDtl.setUnumLicRsId(licCommitteeBean.getUnumComRsId());
                licCommitteeDtl.setUnumLicId(gbltLicCommitteeMst.getUnumLicId());
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
        if (licCommitteeRsId == null || facultyId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Lic Committee Ruleset Id & Faculty Id"));
        }

        List<GbltLicCommitteeRuleSetMst> gbltLicCommitteeRuleSetMst = licCommitteeRuleSetMstRespository.findByUnumComRsIdAndUnumIsValidAndUnumUnivId(
                licCommitteeRsId, 1, RequestUtility.getUniversityId()
        );
        if (gbltLicCommitteeRuleSetMst.isEmpty()) {
            throw new ApplicationException(language.message("Lic Committee Ruleset not found"));
        }

        List<GbltLicCommitteeRuleSetDtl> gbltLicCommitteeRuleSetDtl = licCommitteeRuleSetDtlRespository.findByUnumComRsIdAndUnumIsValidAndUnumUnivId(
                licCommitteeRsId, 1, RequestUtility.getUniversityId()
        );

        if (gbltLicCommitteeRuleSetDtl.isEmpty()) {
            throw new ApplicationException(language.message("Lic Committee Ruleset Details not found"));
        }

        Map<Integer, String> committeeRoleMapList = committeeRoleRepository.getAllCommitteeRoles(RequestUtility.getUniversityId()).stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

        // Get All Teachers
        List<EmployeeBean> teachers = List.of(restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHERS, EmployeeBean[].class));
        if (teachers.isEmpty()) {
            return ServiceResponse.errorResponse("Unable to get Teachers data");
        }

        // Get Teacher profiles
        EmployeeProfileBean[] teacherProfiles = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_PROFILE, EmployeeProfileBean[].class);
        if (teacherProfiles == null) {
            return ServiceResponse.errorResponse("Unable to get Teacher Profiles");
        }

        Map<Long, Set<Integer>> facultyWiseTeachers = Arrays.stream(teacherProfiles).filter(employeeProfileBean -> employeeProfileBean.getUnumFacultyId() != null)
                .collect(Collectors.toMap(EmployeeProfileBean::getUnumEmpId, EmployeeProfileBean -> {
                    Set<Integer> ids = new HashSet<>();
                    ids.add(EmployeeProfileBean.getUnumFacultyId());
                    return ids;
                }, (e1, e2) -> {
                    e1.addAll(e2);
                    return e1;

                }));

        // Get Teachers current Details
        EmployeeCurrentDetailBean[] teacherCurrentDetails = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHER_CURRENT_DETAILS, EmployeeCurrentDetailBean[].class);
        if (teacherCurrentDetails == null) {
            return ServiceResponse.errorResponse("Unable to get Teachers Current Details");
        }

        Map<Long, Integer> designationWiseTeachers = Arrays.stream(teacherCurrentDetails)
                .filter(employeeCurrentDetailBean -> employeeCurrentDetailBean.getUnumEmpDesigid() != null)
                .collect(Collectors.toMap(EmployeeCurrentDetailBean::getUnumEmpId, EmployeeCurrentDetailBean::getUnumEmpDesigid));

        List<LicCommitteeRuleSetDtlBean> licCommitteeRulesetDtlBeanList = gbltLicCommitteeRuleSetDtl.stream().map(licCommitterulesetDtl -> {
            LicCommitteeRuleSetDtlBean licCommitteeRuleSetDtlBean = BeanUtils.copyProperties(licCommitterulesetDtl, LicCommitteeRuleSetDtlBean.class);
            licCommitteeRuleSetDtlBean.setUstrRoleName(committeeRoleMapList.getOrDefault(licCommitteeRuleSetDtlBean.getUnumRoleId(), ""));
            licCommitteeRuleSetDtlBean.setUnumNoOfMembers(gbltLicCommitteeRuleSetMst.get(0).getUnumNoOfMembers());
            licCommitteeRuleSetDtlBean.setUnumLicRsId(licCommitteeRuleSetDtlBean.getUnumComRsId());  // changes addded for sync between Lic ruleset and lic committee
            licCommitteeRuleSetDtlBean.setUnumLicRsDtlId(licCommitteeRuleSetDtlBean.getUnumComRsDtlId());  // changes addded for sync between Lic ruleset and lic committee
            int comRsFacultyId = licCommitterulesetDtl.getUnumRoleCfacultyId();
            List<EmployeeBean> finalTeacherList = teachers;
            if (comRsFacultyId != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean ->
                        facultyWiseTeachers.get(employeeBean.getUnumEmpId()) != null && (comRsFacultyId == 1 ? facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId) : !facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId))
                ).collect(Collectors.toList());
            }
            if (licCommitterulesetDtl.getUnumRoleDepartmentId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> employeeBean.getUnumDeptId() != null && Objects.equals(employeeBean.getUnumDeptId(), licCommitterulesetDtl.getUnumRoleDepartmentId())
                ).toList();
            }
            if (licCommitterulesetDtl.getUnumRolePostId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> designationWiseTeachers.get(employeeBean.getUnumEmpId()) != null && designationWiseTeachers.containsValue(licCommitterulesetDtl.getUnumRolePostId())
                ).toList();
            }
            List<ComboBean> teachersCombo = finalTeacherList.stream().map(employeeBean -> new ComboBean(employeeBean.getUnumEmpId().toString(), employeeBean.getUstrEmpName())).toList();
            licCommitteeRuleSetDtlBean.setTeacherCombo(teachersCombo);
            return licCommitteeRuleSetDtlBean;
        }).toList();

        return ServiceResponse.successObject(licCommitteeRulesetDtlBeanList);
    }

    public List<LicCommitteeBean> getAllLicCommitee() {
        List<GbltLicCommitteeMst> gbltLicCommitteeRuleSetMst = licCommitteetMstRespository.findByUnumIsValidAndUnumUnivId(
                1, RequestUtility.getUniversityId()
        );
        List<LicCommitteeBean> licCommitteeBeans = BeanUtils.copyListProperties(gbltLicCommitteeRuleSetMst, LicCommitteeBean.class);

        // Get All Stream Data
        List<StreamBean> streamData = List.of(restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_STREAMS, StreamBean[].class));
        Map<Long, @NotNull(message = "Stream Name is mandatory") String> streamDataMap = streamData.stream().collect(Collectors.toMap(StreamBean::getUnumStreamId, StreamBean::getUstrStreamFname));

        // Get All Stream Data
        List<FacultyBean> facultyBean = List.of(restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_FACULTY, FacultyBean[].class));
        Map<Long, String> facultyDataMap = facultyBean.stream().collect(Collectors.toMap(FacultyBean::getUnumCfacultyId, FacultyBean::getUstrCfacultyFname));

        List<GbltLicCommitteeRuleSetMst> licRuleSetDtl = licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValid(RequestUtility.getUniversityId(), 1);
        Map<Long, String> licRuleSetNameMap = licRuleSetDtl.stream().collect(Collectors.toMap(GbltLicCommitteeRuleSetMst::getUnumComRsId, GbltLicCommitteeRuleSetMst::getUstrComRsName));

        for (LicCommitteeBean licCommitteeBean : licCommitteeBeans) {
            licCommitteeBean.setUstrLicRsName(licRuleSetNameMap.getOrDefault(licCommitteeBean.getUnumComRsId(), ""));
            licCommitteeBean.setUstrLicCfacultyName(facultyDataMap.getOrDefault(licCommitteeBean.getUnumLicCfacultyId(), ""));
            licCommitteeBean.setUstrStreamName(streamDataMap.getOrDefault(licCommitteeBean.getUnumStreamId(), ""));
        }
        return licCommitteeBeans;
    }

    public ServiceResponse getLicCommitteeByid(Long UnumLicId) {
        GbltLicCommitteeMst gbltLicCommitteeMst = licCommitteetMstRespository.findByUnumIsValidAndUnumUnivIdAndUnumLicId(1, RequestUtility.getUniversityId(), UnumLicId);
        LicCommitteeBean licCommitteeBean = BeanUtils.copyProperties(gbltLicCommitteeMst, LicCommitteeBean.class);

        List<GbltLicCommitteeMemberDtl> gbltLicCommitteeMemberDtl = licCommitteetDtlRespository.findByUnumIsValidAndUnumUnivIdAndUnumLicId(1, RequestUtility.getUniversityId(), UnumLicId);
        List<LicCommitteeDtlBean> licCommitteeDtlBean = BeanUtils.copyListProperties(gbltLicCommitteeMemberDtl, LicCommitteeDtlBean.class);
        licCommitteeBean.setLicCommitteeDtlBean(licCommitteeDtlBean);
        return ServiceResponse.successObject(
                licCommitteeBean
        );

    }

    @Transactional
    public ServiceResponse updateLicCommittee(LicCommitteeBean licCommitteeBean) {
        List<GbltLicCommitteeMst> licCommitteeMst = licCommitteetMstRespository.findByUnumIsValidInAndUstrLicNameIgnoreCaseNotAndUnumUnivId(
                List.of(1, 2), licCommitteeBean.getUstrLicName(), licCommitteeBean.getUnumUnivId());
        if (licCommitteeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee ", licCommitteeBean.getUstrLicName()));
        }
        int slNo = 1;
        Set<Long> members = new HashSet<>();
        for (LicCommitteeDtlBean licCommitteeDtlBean : licCommitteeBean.getLicCommitteeDtlBean()) {
            if (licCommitteeDtlBean.getUnumLicPref1Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref2Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref3Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref4Empid() == null &&
                    licCommitteeDtlBean.getUnumLicPref5Empid() == null) {
                return ServiceResponse.errorResponse("No employee selected for S.No." + slNo);
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref1Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref1Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref2Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref2Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref3Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref3Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref4Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref4Empid()));
            }
            if (isDuplicateMember(members, licCommitteeDtlBean.getUnumLicPref5Empid())) {
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : " + licCommitteeDtlBean.getUnumLicPref5Empid()));
            }
            slNo++;
        }
        // Create Log
        Integer noOfRecordsAffected = licCommitteetMstRespository.createLog(licCommitteeBean.getUnumLicId());
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Lic Committee", licCommitteeBean.getUnumLicId()));
        }
        licCommitteeBean.setUnumIsValid(1);
        GbltLicCommitteeMst gbltLicCommitteeMst = BeanUtils.copyProperties(licCommitteeBean, GbltLicCommitteeMst.class);
        licCommitteetMstRespository.saveAndFlush(gbltLicCommitteeMst);
        List<LicCommitteeDtlBean> committeeList = licCommitteeBean.getLicCommitteeDtlBean();
        if (!committeeList.isEmpty()) {
            committeeList.forEach(commList -> {
                commList.setUnumIsValid(1);
                commList.setUnumUnivId(RequestUtility.getUniversityId());
                commList.setUnumEntryUid(RequestUtility.getUserId());
                commList.setUdtEntryDate(new Date());
                commList.setUnumLicId(licCommitteeBean.getUnumLicId());
            });
            licCommitteetDtlRespository.createLog(licCommitteeBean.getUnumLicId());
            List<GbltLicCommitteeMemberDtl> gbltLicCommitteeMemberDtl = BeanUtils.copyListProperties(committeeList, GbltLicCommitteeMemberDtl.class);
            licCommitteetDtlRespository.saveAll(gbltLicCommitteeMemberDtl);
        }
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Lic Committee Save")).build();

    }

    private boolean isDuplicateMember(Set<Long> memberIds, Long memberIdToCheck) {
        if (memberIdToCheck == null)
            return false;

        if (memberIds.contains(memberIdToCheck))
            return true;

        memberIds.add(memberIdToCheck);
        return false;
    }

    public ResponseEntity<?> print(Long committeeId) {
        GbltLicCommitteeMst gbltLicCommitteeMst = licCommitteetMstRespository.findByUnumIsValidAndUnumUnivIdAndUnumLicId(1, RequestUtility.getUniversityId(), committeeId);

        Map<Integer, String> committeeRoleMap = committeeRoleRepository.getAllCommitteeRoles(RequestUtility.getUniversityId()).stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

        List<GbltLicCommitteeMemberDtl> gbltLicCommitteeMemberDtl = licCommitteetDtlRespository.findByUnumIsValidAndUnumUnivIdAndUnumLicId(1, RequestUtility.getUniversityId(), committeeId);
        List<LicCommitteeDtlBean> licCommitteeDtlBean = gbltLicCommitteeMemberDtl.stream()
                .map(committeeDetail -> {
                    LicCommitteeDtlBean committeeDtlBean = BeanUtils.copyProperties(committeeDetail, LicCommitteeDtlBean.class);
                    // Member Name
                    String empName = "";
                    Long empId = 0L;
                    if (committeeDtlBean.getUnumLicPref1Empname() != null) {
                        empName = committeeDtlBean.getUnumLicPref1Empname();
                        empId = committeeDtlBean.getUnumLicPref1Empid();
                    } else if (committeeDtlBean.getUnumLicPref2Empname() != null) {
                        empName = committeeDtlBean.getUnumLicPref2Empname();
                        empId = committeeDtlBean.getUnumLicPref2Empid();
                    } else if (committeeDtlBean.getUnumLicPref3Empname() != null) {
                        empName = committeeDtlBean.getUnumLicPref3Empname();
                        empId = committeeDtlBean.getUnumLicPref3Empid();
                    } else if (committeeDtlBean.getUnumLicPref4Empname() != null) {
                        empName = committeeDtlBean.getUnumLicPref4Empname();
                        empId = committeeDtlBean.getUnumLicPref4Empid();
                    } else if (committeeDtlBean.getUnumLicPref5Empname() != null) {
                        empName = committeeDtlBean.getUnumLicPref5Empname();
                        empId = committeeDtlBean.getUnumLicPref5Empid();
                    }

                    // Role Name
                    String roleName = committeeRoleMap.getOrDefault(committeeDtlBean.getUnumLicRoleId(), "");

                    // College Name
                    String collegeName = "";
                    String collegeAddress = "";
                    String pinCode = "";
                    String mobileNo = "";
                    String email = "";
                    EmployeeBean employee = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_TEACHER_BY_ID + empId, EmployeeBean.class);
                    if (employee != null) {
                        Long collegeId = employee.getUnumCollegeId();
                        // Get College of employee
                        if (collegeId != null && !collegeId.equals(0L)) {
                            CollegeBean collegeBean = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_COLLEGE + collegeId, CollegeBean.class);
                            if (collegeBean != null) {
                                collegeName = collegeBean.getUstrColFname();
                                collegeAddress = Optional.ofNullable(collegeBean.getUstrAddress2()).orElse("");
                                pinCode = Optional.ofNullable(collegeBean.getUnumPincode()).map(pin -> " - " + pin).orElse("");
                            }
                        }

                        mobileNo = Optional.ofNullable(employee.getUnumMobileNo()).map(String::valueOf).orElse("");
                        email = Optional.ofNullable(employee.getUstrTEmailid()).orElse("");
                    }

                    committeeDtlBean.setUnumLicPref1Empname(empName + "\n" + collegeName + "\n" + collegeAddress + pinCode);
                    committeeDtlBean.setRoleName(roleName + "\nMobile: " + mobileNo + "\nEmail: " + email);
                    return committeeDtlBean;
                })
                .toList();

        Map<String, Object> committeeMap = new HashMap<>();
        committeeMap.put("committee", gbltLicCommitteeMst);
        committeeMap.put("members", licCommitteeDtlBean);
        byte[] pdfBytes = pdfGeneratorUtil.createPdfBytes("lic_committee", committeeMap);
        String fileName = "Committee_" + gbltLicCommitteeMst.getUstrLicName() + ".pdf";

        return ResponseHandler.generateFileResponse(pdfBytes, fileName);
    }
}
