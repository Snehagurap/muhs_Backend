package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.*;
import in.cdac.university.committee.entity.*;
import in.cdac.university.committee.repository.*;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommitteeService {

    @Autowired
    private CommitteeMasterRepository committeeMasterRepository;

    @Autowired
    private CommitteeDetailRepository committeeDetailRepository;

    @Autowired
    private CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    private CommitteeMemberMappingRepository committeeMemberMappingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private Language language;

    @Autowired
    private RestUtility restUtility;

    public ServiceResponse createCommittee(CommitteeBean committeeBean) {
        int noOfMembers = committeeBean.getUnumNoOfMembers();
        if (noOfMembers != committeeBean.getCommitteeDetail().size()) {
            return ServiceResponse.errorResponse(language.message("Not all members details defined"));
        }

        // Check for duplicate
        Optional<GbltCommitteeMst> committeeMstOptional = committeeMasterRepository.duplicateCheck(committeeBean.getUstrComName(),
                committeeBean.getUnumUnivId(), committeeBean.getUdtComStartDate(), committeeBean.getUdtComEndDate());

        if (committeeMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.message("Committee name [" + committeeBean.getUstrComName() + "] already exists for the selected period."));
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

    public List<CommitteeBean> getCommitteeList() {
        Integer universityId = RequestUtility.getUniversityId();

        return BeanUtils.copyListProperties(
                committeeMasterRepository.findByUnumUnivIdAndUnumIsvalidOrderByUstrComNameAsc(universityId, 1),
                CommitteeBean.class
        );
    }

    public List<CommitteeBean> getCommitteeCombo() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, currentYear - 1);

        return BeanUtils.copyListProperties(
                committeeMasterRepository.activeCommitteeList(cal.getTime(), RequestUtility.getUniversityId()),
                CommitteeBean.class
        );
    }

    public ServiceResponse getCommittee(Long committeeId) {
        if (committeeId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Committee Id"));
        }

        Optional<GbltCommitteeMst> committeeMstOptional = committeeMasterRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1, committeeId, RequestUtility.getUniversityId());

        if (committeeMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", committeeId));
        }

        CommitteeBean committeeBean = BeanUtils.copyProperties(committeeMstOptional.get(), CommitteeBean.class);

        List<GbltCommitteeDtl> committeeDtlList = committeeDetailRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1, committeeId, RequestUtility.getUniversityId());
        if (!committeeDtlList.isEmpty()) {
            Map<Integer, String> committeeRoles = committeeRoleRepository.findAll().stream()
                            .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

            List<CommitteeDetailBean> committeeDetailBeans = committeeDtlList.stream().map(
                    committeeDetail -> {
                        CommitteeDetailBean committeeDetailBean = BeanUtils.copyProperties(committeeDetail, CommitteeDetailBean.class);
                        committeeDetailBean.setRoleName(committeeRoles.getOrDefault(committeeDetail.getUnumRoleId(), ""));
                        return committeeDetailBean;
                    }
            ).toList();

            committeeBean.setCommitteeDetail(committeeDetailBeans);
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(committeeBean)
                .build();
    }

    public ServiceResponse getCommitteeByEventIdWithMembers(Long eventId) {
        if (eventId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Event Id"));
        }

        Optional<GbltEventMst> eventBeanOptional = eventRepository.findById(new GbltEventMstPK(eventId, 1));
        if (eventBeanOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Event", eventId));
        }
        Long committeeId = eventBeanOptional.get().getUnumComid();

        Optional<GbltCommitteeMst> committeeMstOptional = committeeMasterRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1, committeeId, RequestUtility.getUniversityId());

        if (committeeMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", committeeId));
        }

        CommitteeBean committeeBean = BeanUtils.copyProperties(committeeMstOptional.get(), CommitteeBean.class);

        List<GbltCommitteeDtl> committeeDtlList = committeeDetailRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1, committeeId, RequestUtility.getUniversityId());
        if (!committeeDtlList.isEmpty()) {
            Set<Integer> designationIds = new HashSet<>();
            Set<Integer> facultyIds = new HashSet<>();
            for (GbltCommitteeDtl committeeDtl: committeeDtlList) {
                designationIds.add(committeeDtl.getUnumRolePostId());
                facultyIds.add(committeeDtl.getUnumRoleCfacultyId());
            }

            // Get Teacher profiles
            EmployeeProfileBean[] teacherProfiles = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_TEACHER_PROFILE, facultyIds, EmployeeProfileBean[].class);
            if (teacherProfiles == null) {
                return ServiceResponse.errorResponse("Unable to get Teacher Profiles");
            }

            Map<Integer, List<EmployeeProfileBean>> facultyWiseTeachers = Arrays.stream(teacherProfiles)
                        .filter(employeeProfileBean -> employeeProfileBean.getUnumFacultyId() != null)
                        .collect(Collectors.groupingByConcurrent(EmployeeProfileBean::getUnumFacultyId));

            // Get Teachers current Details
            EmployeeCurrentDetailBean[] teacherCurrentDetails = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_TEACHER_CURRENT_DETAILS, designationIds, EmployeeCurrentDetailBean[].class);
            if (teacherCurrentDetails == null) {
                return ServiceResponse.errorResponse("Unable to get Teachers Current Details");
            }

            Map<Integer, List<EmployeeCurrentDetailBean>> designationWiseTeachers = Arrays.stream(teacherCurrentDetails)
                    .filter(employeeCurrentDetailBean -> employeeCurrentDetailBean.getUnumEmpDesigid() != null)
                    .collect(Collectors.groupingByConcurrent(EmployeeCurrentDetailBean::getUnumEmpDesigid));

            // Get All Teachers
            ComboBean[] teachers = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHERS, ComboBean[].class);
            if (teachers == null) {
                return ServiceResponse.errorResponse("Unable to get Teachers data");
            }

            Map<String, ComboBean> teacherMap = Arrays.stream(teachers)
                        .collect(Collectors.toMap(ComboBean::getKey, Function.identity(), (v1, v2) -> v2));

            Map<Integer, String> committeeRoles = committeeRoleRepository.findAll().stream()
                    .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

            List<CommitteeDetailBean> committeeDetailBeans = committeeDtlList.stream().map(
                    committeeDetail -> getCommitteeDetailBean(facultyWiseTeachers, designationWiseTeachers, teacherMap, committeeRoles, committeeDetail)
            ).toList();

            committeeBean.setCommitteeDetail(committeeDetailBeans);
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(committeeBean)
                .build();
    }

    private CommitteeDetailBean getCommitteeDetailBean(Map<Integer, List<EmployeeProfileBean>> facultyWiseTeachers, Map<Integer, List<EmployeeCurrentDetailBean>> designationWiseTeachers, Map<String, ComboBean> teacherMap, Map<Integer, String> committeeRoles, GbltCommitteeDtl committeeDetail) {
        CommitteeDetailBean committeeDetailBean = BeanUtils.copyProperties(committeeDetail, CommitteeDetailBean.class);
        committeeDetailBean.setRoleName(committeeRoles.getOrDefault(committeeDetail.getUnumRoleId(), ""));
        Integer facultyId = committeeDetailBean.getUnumRoleCfacultyId();
        Integer departmentId = committeeDetailBean.getUnumRoleDepartmentId();
        Integer designationId = committeeDetailBean.getUnumRolePostId();
        List<Long> filteredTeacherIds = new ArrayList<>();
        if (facultyId != 0) {
            if (facultyWiseTeachers.containsKey(facultyId))
                filteredTeacherIds = facultyWiseTeachers.get(facultyId)
                        .stream().map(EmployeeProfileBean::getUnumEmpId)
                        .collect(Collectors.toList());
        }

        if (designationId != 0) {
            if (designationWiseTeachers.containsKey(designationId)) {
                List<Long> temp = designationWiseTeachers.get(designationId)
                        .stream().map(EmployeeCurrentDetailBean::getUnumEmpId)
                        .toList();
                if (!filteredTeacherIds.isEmpty()) {
                    filteredTeacherIds.retainAll(temp);
                } else {
                    filteredTeacherIds = temp;
                }
            }
        }

        List<ComboBean> filteredTeachers = new ArrayList<>();
        if (!filteredTeacherIds.isEmpty()) {
            for (Long teacherId: filteredTeacherIds) {
                if (teacherMap.containsKey(teacherId.toString()))
                    filteredTeachers.add(teacherMap.get(teacherId.toString()));
            }
        }
        committeeDetailBean.setTeachersCombo(filteredTeachers);
        return committeeDetailBean;
    }

    public ServiceResponse saveMemberMapping(CommitteeMemberBean committeeMemberBean) {
        int slNo = 1;
        for (CommitteeMember committeeMember: committeeMemberBean.getMembers()) {
            if (committeeMember.getUnumPreference1Empid() == null
                    && committeeMember.getUnumPreference2Empid() == null
                    && committeeMember.getUnumPreference3Empid() == null
                    && committeeMember.getUnumPreference4Empid() == null) {
                return ServiceResponse.errorResponse("No Employee select for S.No. " + slNo);
            }
            slNo++;
        }

        slNo = 1;
        List<GbltCommitteeMemberDtl> membersToSave = new ArrayList<>();
        for (CommitteeMember committeeMember: committeeMemberBean.getMembers()) {
            GbltCommitteeMemberDtl committeeMemberDtl = BeanUtils.copyProperties(committeeMember, GbltCommitteeMemberDtl.class);
            if (committeeMember.getUnumPreference1Empid() == null)
                committeeMemberDtl.setUnumPreference1Empname(null);
            if (committeeMember.getUnumPreference2Empid() == null)
                committeeMemberDtl.setUnumPreference2Empname(null);
            if (committeeMember.getUnumPreference3Empid() == null)
                committeeMemberDtl.setUnumPreference3Empname(null);
            if (committeeMember.getUnumPreference4Empid() == null)
                committeeMemberDtl.setUnumPreference4Empname(null);
            if (committeeMember.getUnumPreference5Empid() == null)
                committeeMemberDtl.setUnumPreference5Empname(null);

            committeeMemberDtl.setUnumComMemberId(committeeMemberMappingRepository.getNextId());
            committeeMemberDtl.setUnumIsvalid(committeeMemberBean.getUnumIsvalid());
            committeeMemberDtl.setUdtEntryDate(committeeMemberBean.getUdtEntryDate());
            committeeMemberDtl.setUnumComId(committeeMemberBean.getUnumComId());
            committeeMemberDtl.setUnumEntryUid(committeeMemberBean.getUnumEntryUid());
            committeeMemberDtl.setUnumEventid(committeeMemberBean.getUnumEventid());
            committeeMemberDtl.setUnumMemberSno(slNo++);
            committeeMemberDtl.setUnumUnivId(committeeMemberBean.getUnumUnivId());

            membersToSave.add(committeeMemberDtl);
        }

        committeeMemberMappingRepository.saveAll(membersToSave);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Committee Member Mapping"))
                .build();
    }

    public ServiceResponse getCommitteeMemberMappingByEventId(Long eventId) {
        List<GbltCommitteeMemberDtl> gbltCommitteeMemberDtlList = committeeMemberMappingRepository.findByUnumEventidAndUnumIsvalidAndUnumUnivId(eventId,1,RequestUtility.getUniversityId());

        if(gbltCommitteeMemberDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Member" , eventId));
        }

        Optional<GbltCommitteeMst> committeeMstOptional = committeeMasterRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1,gbltCommitteeMemberDtlList.get(0).getUnumComId(), RequestUtility.getUniversityId());

        if (committeeMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", gbltCommitteeMemberDtlList.get(0).getUnumComId()));
        }

        Map<Integer, String> committeeRoles = committeeRoleRepository.findAll().stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

//        committeeRoleRepository.findAll().stream()
//                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, obj -> obj.getUstrRoleFname()));

        List<CommitteeMember> committeeMemberList = gbltCommitteeMemberDtlList.stream().map(
                committeeMember -> {
                    CommitteeMember committeeMember1 = BeanUtils.copyProperties(committeeMember, CommitteeMember.class);
                    committeeMember1.setUstrRolename(committeeRoles.getOrDefault(committeeMember.getUnumRoleId(), ""));
                    return committeeMember1;
                }
        ).toList();
        CommitteeBean committeeBean = BeanUtils.copyProperties(committeeMstOptional.get(), CommitteeBean.class);

        Optional<GbltEventMst> gbltEventMstOptional = eventRepository.findByUnumEventidAndUnumUnivIdAndUnumIsvalid(eventId, RequestUtility.getUniversityId(), 1);

        if (gbltEventMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Event", eventId));
        }

        EventBean eventBean = BeanUtils.copyProperties(gbltEventMstOptional.get() , EventBean.class);

        eventBean.setCommitteeMemberDetail(committeeMemberList);
        committeeBean.setEventDetail(List.of(eventBean));

        return ServiceResponse.builder()
                .status(1)
                .responseObject(committeeBean)
                .build();
    }
}
