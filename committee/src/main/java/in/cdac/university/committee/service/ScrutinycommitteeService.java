package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.*;
import in.cdac.university.committee.entity.*;
import in.cdac.university.committee.exception.ApplicationException;
import in.cdac.university.committee.repository.*;
import in.cdac.university.committee.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScrutinycommitteeService {

    @Autowired
    ScrutinycommitteeMstRepository scrutinycommitteeMstRepository;

    @Autowired
    ScrutinycommitteeMemberDtlRepository scrutinycommitteeMemberDtlRepository;

    @Autowired
    CommitteeRulesetDtlRepository committeeRulesetDtlRepository;

    @Autowired
    CommitteeRulesetMstRepository committeeRulesetMstRepository;

    @Autowired
    CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    Language language;

    @Autowired
    RestUtility restUtility;


    public List<ScrutinycommitteeBean> getScrutinyCommitteeList() {
        List<GbltScrutinycommitteeMst> gbltScrutinycommitteeMstList = scrutinycommitteeMstRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());

        List<GbltCommitteeRulesetMst> gbltCommitteeRulesetMstList = committeeRulesetMstRepository.findByUnumUnivIdAndUnumIsvalid(RequestUtility.getUniversityId(), 1);

        Map<Long, String> committeeRsMap = gbltCommitteeRulesetMstList.stream().collect(Collectors.toMap(
                GbltCommitteeRulesetMst::getUnumComRsId, GbltCommitteeRulesetMst::getUstrComRsName
        ));

        StreamBean[] streamBean = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_STREAMS,  StreamBean[].class);

        Map<Long, String> streamMap = Arrays.stream(streamBean).collect(Collectors.toMap(
                StreamBean::getUnumStreamId, StreamBean::getUstrStreamFname
        ));

        FacultyBean[] facultyBeans = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_FACULTY, FacultyBean[].class);
        Map<Long, String> facultyMap = Arrays.stream(facultyBeans).collect(Collectors.toMap(
                FacultyBean::getUnumCfacultyId, FacultyBean::getUstrCfacultyFname
        ));

        List<ScrutinycommitteeBean> scrutinycommitteeBeanList = gbltScrutinycommitteeMstList.stream().map(gbltScrutinycommitteeMst -> {
            ScrutinycommitteeBean scrutinycommitteeBean  = BeanUtils.copyProperties(gbltScrutinycommitteeMst, ScrutinycommitteeBean.class);
            scrutinycommitteeBean.setUstrComRsName(committeeRsMap.getOrDefault(scrutinycommitteeBean.getUnumComRsId(),""));
            scrutinycommitteeBean.setUstrCfacultyName(facultyMap.getOrDefault(scrutinycommitteeBean.getUnumScomCfacultyId(), ""));
            scrutinycommitteeBean.setUstrStreamName(streamMap.getOrDefault(scrutinycommitteeBean.getUnumStreamId(),""));
            return scrutinycommitteeBean;
        }).toList();
        return scrutinycommitteeBeanList;
    }


    public ServiceResponse getComRsDataForScrutinyComCreation(Long committeeRsId, Integer facultyId) {
        if(committeeRsId == null || facultyId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Committee Ruleset Id & Faculty Id"));
        }
        Optional<GbltCommitteeRulesetMst> gbltCommitteeRulesetMstOptional = committeeRulesetMstRepository.findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(
                committeeRsId, 1, RequestUtility.getUniversityId()
        );
        if(gbltCommitteeRulesetMstOptional.isEmpty()) {
            throw new ApplicationException(language.message("Committee Ruleset not found"));
        }

        List<GbltCommitteeRulesetDtl> gbltCommitteeRulesetDtlList = committeeRulesetDtlRepository.findByUnumComRsIdAndUnumIsvalidAndUnumUnivId(
                committeeRsId, 1, RequestUtility.getUniversityId()
        );

        if(gbltCommitteeRulesetDtlList.isEmpty()) {
            throw new ApplicationException(language.message("Committee Ruleset Details not found"));
        }

        // committee title/role data
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

//        Map<Long, Set<Integer>> facultyWiseTeachers = new HashMap<>();

//        Arrays.stream(teacherProfiles).filter(employeeProfileBean -> employeeProfileBean.getUnumFacultyId()!=null)
//                .forEach(employeeProfileBean -> {
//                    Set<Integer> facultyIds = new HashSet<>();
//                    if(facultyWiseTeachers.containsKey(employeeProfileBean.getUnumEmpId())){
//                        facultyIds = facultyWiseTeachers.get(employeeProfileBean.getUnumEmpId());
//                    }
//                    facultyIds.add(employeeProfileBean.getUnumFacultyId());
//                    facultyWiseTeachers.put(employeeProfileBean.getUnumEmpId(), facultyIds);
//                });



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


        List<CommitteeRulesetDtlBean> committeeRulesetDtlBeanList =gbltCommitteeRulesetDtlList.stream().map(committerulesetDtl -> {
            CommitteeRulesetDtlBean committeeRulesetDtlBean = BeanUtils.copyProperties(committerulesetDtl, CommitteeRulesetDtlBean.class);
            committeeRulesetDtlBean.setUstrRoleName(committeeRoleMapList.getOrDefault(committeeRulesetDtlBean.getUnumRoleId(), ""));
            committeeRulesetDtlBean.setUnumNoOfMembers(gbltCommitteeRulesetMstOptional.get().getUnumNoOfMembers());
            Integer comRsFacultyId = committerulesetDtl.getUnumRoleCfacultyId();
            List<EmployeeBean> finalTeacherList = teachers;

            if(comRsFacultyId != 0){
                finalTeacherList = finalTeacherList.stream().filter(employeeBean ->
                        facultyWiseTeachers.get(employeeBean.getUnumEmpId()) != null && (comRsFacultyId == 1 ? facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId) : !facultyWiseTeachers.get(employeeBean.getUnumEmpId()).contains(facultyId))
                ).collect(Collectors.toList());
            }
            if(committerulesetDtl.getUnumRoleDepartmentId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> employeeBean.getUnumDeptId()!=null && Objects.equals(employeeBean.getUnumDeptId(), committerulesetDtl.getUnumRoleDepartmentId())
                ).toList();
            }
            if(committerulesetDtl.getUnumRolePostId() != 0) {
                finalTeacherList = finalTeacherList.stream().filter(employeeBean -> designationWiseTeachers.get(employeeBean.getUnumEmpId())!=null && designationWiseTeachers.containsValue(committerulesetDtl.getUnumRolePostId())
                ).toList();
            }

            List<ComboBean> teachersCombo = finalTeacherList.stream().map(employeeBean ->  new ComboBean(employeeBean.getUnumEmpId().toString(), employeeBean.getUstrEmpName())).toList();

            committeeRulesetDtlBean.setTeacherCombo(teachersCombo);
            return committeeRulesetDtlBean;
        }).toList();

        return ServiceResponse.successObject(committeeRulesetDtlBeanList);
    }

    private boolean isDuplicateMember(Set<Long> memberIds, Long memberIdToCheck) {
        if(memberIdToCheck==null)
            return false;

        if(memberIds.contains(memberIdToCheck))
            return true;

        memberIds.add(memberIdToCheck);
        return false;
    }

    @Transactional
    public ServiceResponse saveScrutinyCommittee(ScrutinycommitteeBean scrutinycommitteeBean) {
        int slNo = 1;
        Set<Long> members = new HashSet<>();
        for(ScrutinycommitteeMemberDtlBean sComMemberDtlBean : scrutinycommitteeBean.getScrutinyComMemberDtl()) {
            if(sComMemberDtlBean.getUnumScomPref1Empid()==null &&
                    sComMemberDtlBean.getUnumScomPref2Empid()==null &&
                    sComMemberDtlBean.getUnumScomPref3Empid()==null &&
                    sComMemberDtlBean.getUnumScomPref4Empid()==null &&
                    sComMemberDtlBean.getUnumScomPref5Empid()==null &&
                    sComMemberDtlBean.getUnumScomPrefOtherEmpid()==null) {
                return ServiceResponse.errorResponse("No employee selected for S.No." + slNo);
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPref1Empid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPref1Empname()));
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPref2Empid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPref2Empname()));
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPref3Empid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPref3Empname()));
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPref4Empid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPref4Empname()));
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPref5Empid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPref5Empname()));
            }
            if(isDuplicateMember(members,sComMemberDtlBean.getUnumScomPrefOtherEmpid())){
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected : "+sComMemberDtlBean.getUstrScomPrefOtherEmpname()));
            }

            slNo++;
        }
        Long scomId = null;
        // Save
        if(scrutinycommitteeBean.getIsSave().equals(1)){
            scomId = scrutinycommitteeMstRepository.getNextId();
            GbltScrutinycommitteeMst gbltScrutinycommitteeMst = BeanUtils.copyProperties(scrutinycommitteeBean, GbltScrutinycommitteeMst.class);
            gbltScrutinycommitteeMst.setUnumScomId(scomId);
            scrutinycommitteeMstRepository.save(gbltScrutinycommitteeMst);
        }
        // Modify
        else {
            if(scrutinycommitteeBean.getIsSave().equals(0)){
                if(scrutinycommitteeBean.getUnumScomId() == null) {
                    return ServiceResponse.errorResponse(language.mandatory("Srutiny Committee Id "));
                }
                // create Log Scrutiny committee Mst
                scomId = scrutinycommitteeBean.getUnumScomId();
                int noOfRecordsAffected = scrutinycommitteeMstRepository.createLog(List.of(scomId));
                if(noOfRecordsAffected == 0) {
                    throw new ApplicationException(language.notFoundForId("Scrutiny Committee", scomId));
                }

                // create Log Scrutiny Committee Member Dtl
                int noOfRecordsAffectedDTL = scrutinycommitteeMemberDtlRepository.createLog(List.of(scomId));
                if(noOfRecordsAffectedDTL == 0) {
                    throw new ApplicationException(language.notFoundForId("Scrutiny Committee Members", scomId));
                }

                //save new data
                GbltScrutinycommitteeMst gbltScrutinycommitteeMst = BeanUtils.copyProperties(scrutinycommitteeBean, GbltScrutinycommitteeMst.class);
                scrutinycommitteeMstRepository.save(gbltScrutinycommitteeMst);
            }

        }

        // Scrutiny Committee Member

        // Modify
        if(scrutinycommitteeBean.getIsSave().equals(0)){
            if(scomId == null) {
                return ServiceResponse.errorResponse(language.mandatory("Srutiny Committee Id "));
            }
            // create Log
//            int noOfRecordsAffected = scrutinycommitteeMemberDtlRepository.createLog(List.of(scomId));
//            if(noOfRecordsAffected == 0) {
//                throw new ApplicationException(language.notFoundForId("Scrutiny Committee Members", scomId));
//            }

        }




        slNo = 1;
        List<GbltScrutinycommitteeMemberDtl> membersToSave = new ArrayList<>();
        List<Long> chairmen = new ArrayList<>();
        List<Long> member1 = new ArrayList<>();
        List<Long> member2 = new ArrayList<>();
        List<Long> committeeMembers = new ArrayList<>();
        boolean isMember1 = true;
        for (ScrutinycommitteeMemberDtlBean sComMemberDtlBean: scrutinycommitteeBean.getScrutinyComMemberDtl()) {
            GbltScrutinycommitteeMemberDtl gbltScrutinycommitteeMemberDtl = BeanUtils.copyProperties(sComMemberDtlBean, GbltScrutinycommitteeMemberDtl.class);
            if (sComMemberDtlBean.getUnumScomPref1Empid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPref1Empname(null);
            }
            else{
                committeeMembers.add(sComMemberDtlBean.getUnumScomPref1Empid());
            }

            if (sComMemberDtlBean.getUnumScomPref2Empid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPref2Empname(null);
            }
            else {
                committeeMembers.add(sComMemberDtlBean.getUnumScomPref2Empid());
            }

            if (sComMemberDtlBean.getUnumScomPref3Empid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPref3Empname(null);
            }
            else{
                committeeMembers.add(sComMemberDtlBean.getUnumScomPref3Empid());
            }

            if (sComMemberDtlBean.getUnumScomPref4Empid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPref4Empname(null);
            }
            else{
                committeeMembers.add(sComMemberDtlBean.getUnumScomPref4Empid());
            }

            if (sComMemberDtlBean.getUnumScomPref5Empid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPref5Empname(null);
            }
            else{
                committeeMembers.add(sComMemberDtlBean.getUnumScomPref5Empid());
            }

            if (sComMemberDtlBean.getUnumScomPrefOtherEmpid() == null) {
                gbltScrutinycommitteeMemberDtl.setUstrScomPrefOtherEmpname(null);
            }
            else{
                committeeMembers.add(sComMemberDtlBean.getUnumScomPrefOtherEmpid());
            }


            gbltScrutinycommitteeMemberDtl.setUnumScomMemberId(scrutinycommitteeMemberDtlRepository.getNextId());
            gbltScrutinycommitteeMemberDtl.setUnumIsvalid(scrutinycommitteeBean.getUnumIsvalid());
            gbltScrutinycommitteeMemberDtl.setUdtEntryDate(scrutinycommitteeBean.getUdtEntryDate());
            gbltScrutinycommitteeMemberDtl.setUnumScomId(scomId);
            gbltScrutinycommitteeMemberDtl.setUnumEntryUid(scrutinycommitteeBean.getUnumEntryUid());

            gbltScrutinycommitteeMemberDtl.setUnumScomMemberSno(slNo++);
            gbltScrutinycommitteeMemberDtl.setUnumUnivId(scrutinycommitteeBean.getUnumUnivId());

            List<Long> employees = null;
            if (sComMemberDtlBean.getUnumRoleId()==1) {
                employees = chairmen;
            } else if (sComMemberDtlBean.getUnumRoleId()==2 || sComMemberDtlBean.getUnumRoleId()==3) {
                if (isMember1) {
                    employees = member1;
                    isMember1 = false;
                } else {
                    employees = member2;
                }
            }
            if (employees != null) {
                addMember(employees, sComMemberDtlBean.getUnumScomPref1Empid());
                addMember(employees, sComMemberDtlBean.getUnumScomPref2Empid());
                addMember(employees, sComMemberDtlBean.getUnumScomPref3Empid());
                addMember(employees, sComMemberDtlBean.getUnumScomPref4Empid());
                addMember(employees, sComMemberDtlBean.getUnumScomPref5Empid());
                addMember(employees, sComMemberDtlBean.getUnumScomPrefOtherEmpid());
            }

            membersToSave.add(gbltScrutinycommitteeMemberDtl);
        }

        scrutinycommitteeMemberDtlRepository.saveAll(membersToSave);

        // Update Committee Flag in Employee Master
        EmployeeBean empBean = new EmployeeBean();
        if (!chairmen.isEmpty()) {
            empBean.setEmployeesToFlag(chairmen);
            String response = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_UPDATE_CHAIRMEN_FLAG, empBean, String.class);
            if (response == null) {
                log.error("Unable to update Chairmen flag for members");
            }
        }

        if (!member1.isEmpty()) {
            empBean.setEmployeesToFlag(member1);
            String response = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_UPDATE_MEMBER1_FLAG, empBean, String.class);
            if (response == null) {
                log.error("Unable to update Member 1 flag for members");
            }
        }

        if (!member2.isEmpty()) {
            empBean.setEmployeesToFlag(member2);
            String response = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_UPDATE_MEMBER2_FLAG, empBean, String.class);
            if (response == null) {
                log.error("Unable to update Member 2 flag for members");
            }
        }

        if (!committeeMembers.isEmpty()) {
            empBean.setEmployeesToFlag(committeeMembers);
            String response = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_UPDATE_COMMITTEE_SELECTION_FLAG, empBean, String.class);
            if (response == null) {
                log.error("Unable to update Committee Selection flag for members");
            }
        }

        if(scrutinycommitteeBean.getIsSave().equals(1)) {
            return ServiceResponse.successMessage(language.saveSuccess("Scrutiny Committee Member Mapping"));
        }
        else{
            return ServiceResponse.successMessage(language.updateSuccess("Scrutiny Committee Member Mapping"));
        }
    }

    private void addMember(List<Long> members, Long employeeId) {
        if (employeeId != null)
            members.add(employeeId);
    }


    public ServiceResponse getScrutinyCommitteeById(Long scomId) {
        if(scomId == null){
            return ServiceResponse.errorResponse(language.mandatory("Scrutiny Committee Id "));
        }

        Optional<GbltScrutinycommitteeMst> gbltScrutinycommitteeMstOptional = scrutinycommitteeMstRepository.findByUnumScomIdAndUnumIsvalidAndUnumUnivId(
                scomId, 1, RequestUtility.getUniversityId()
        );

        if(gbltScrutinycommitteeMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Scrutiny Committee", scomId));
        }

        ScrutinycommitteeBean scrutinycommitteeBean = BeanUtils.copyProperties(gbltScrutinycommitteeMstOptional.get(), ScrutinycommitteeBean.class);

        List<GbltScrutinycommitteeMemberDtl> gbltScrutinycommitteeMemberDtlList = scrutinycommitteeMemberDtlRepository.findByUnumScomIdAndUnumIsvalidAndUnumUnivId(
                scomId, 1, RequestUtility.getUniversityId()
        );

        if(gbltScrutinycommitteeMemberDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Scrutiny Committee Members", scomId));
        }


        // committee title/role data
        Map<Integer, String> committeeRoleMapList = committeeRoleRepository.getAllCommitteeRoles(RequestUtility.getUniversityId()).stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId,GmstCommitteeRoleMst::getUstrRoleFname));

        List<ScrutinycommitteeMemberDtlBean> scomMemberDtlBeanList = gbltScrutinycommitteeMemberDtlList.stream().map(gbltScrutinycommitteeMemberDtl -> {
            ScrutinycommitteeMemberDtlBean scommitteeMemberDtlBean = BeanUtils.copyProperties(gbltScrutinycommitteeMemberDtl, ScrutinycommitteeMemberDtlBean.class);
            scommitteeMemberDtlBean.setUstrRoleName(committeeRoleMapList.getOrDefault(scommitteeMemberDtlBean.getUnumRoleId(),""));
            return scommitteeMemberDtlBean;
        }).toList();


        scrutinycommitteeBean.setScrutinyComMemberDtl(scomMemberDtlBeanList);

        return ServiceResponse.successObject(scrutinycommitteeBean);
    }

}

