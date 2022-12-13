package in.cdac.university.committee.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import in.cdac.university.committee.bean.*;
import in.cdac.university.committee.entity.*;
import in.cdac.university.committee.repository.*;
import in.cdac.university.committee.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
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

    public List<CommitteeBean> getCommitteeList(Integer committeeTypeId) {
        Integer universityId = RequestUtility.getUniversityId();

        return BeanUtils.copyListProperties(
                committeeMasterRepository.findByUnumUnivIdAndUnumIsvalidAndUnumComtypeIdOrderByUstrComNameAsc(universityId, 1, committeeTypeId),
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
            EmployeeBean[] teachers = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_TEACHERS, EmployeeBean[].class);
            if (teachers == null) {
                return ServiceResponse.errorResponse("Unable to get Teachers data");
            }

            Map<Long, EmployeeBean> teacherMap = Arrays.stream(teachers)
                        .filter(teacher -> teacher.getUnumIsSelected() == null || teacher.getUnumIsSelected() == 0)
                        .collect(Collectors.toMap(EmployeeBean::getUnumEmpId, Function.identity(), (v1, v2) -> v2));

            Map<Integer, String> committeeRoles = committeeRoleRepository.findAll().stream()
                    .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname, (u, v) -> u));

            AtomicInteger sno = new AtomicInteger(0);
            AtomicInteger memberCount = new AtomicInteger(1);
            List<CommitteeDetailBean> committeeDetailBeans = committeeDtlList.stream().map(
                    committeeDetail -> {
                        if (committeeBean.getUnumIsFormulaBased() == null || committeeBean.getUnumIsFormulaBased() == 0) {
                            CommitteeDetailBean committeeDetailBean = mapTeacherManual(teachers, committeeRoles, committeeDetail, committeeDetail.getUnumRoleId());

                            if (committeeDetailBean.getTeachersCombo().isEmpty())
                                committeeDetailBean = mapTeacherByRule(facultyWiseTeachers, designationWiseTeachers, teacherMap, committeeRoles, committeeDetail);

                            return committeeDetailBean;


//                            if (committeeDetail.getUnumRoleId() == 10)
//                                sno.set(1);
//                            else if (committeeDetail.getUnumRoleId() == 11)
//                                sno.set(memberCount.addAndGet(1));
//                            else
//                                sno.set(0);
//                            if (sno.get() == 0)
//                                return mapTeacherByRule(facultyWiseTeachers, designationWiseTeachers, teacherMap, committeeRoles, committeeDetail);
//                            else
//                                return mapTeacherManual(teachers, committeeRoles, committeeDetail, committeeDetail.getUnumRoleId());
                        } else {
                            return mapTeacherByRule(facultyWiseTeachers, designationWiseTeachers, teacherMap, committeeRoles, committeeDetail);
                        }
                    }
            ).toList();

            committeeBean.setCommitteeDetail(committeeDetailBeans);
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(committeeBean)
                .build();
    }

    private CommitteeDetailBean mapTeacherManual(EmployeeBean[] teachers, Map<Integer, String> committeeRoles, GbltCommitteeDtl committeeDetail, int sno) {
        CommitteeDetailBean committeeDetailBean = BeanUtils.copyProperties(committeeDetail, CommitteeDetailBean.class);
        committeeDetailBean.setRoleName(committeeRoles.getOrDefault(committeeDetail.getUnumRoleId(), ""));
        List<ComboBean> filteredTeachers = Arrays.stream(teachers)
                .filter(teacher -> teacher.getUnumIsSelectedfor() != null && teacher.getUnumIsSelectedfor() == sno)
                .map(teacher -> new ComboBean(teacher.getUnumEmpId().toString(), teacher.getUstrEmpName()))
                .collect(Collectors.toList());

        committeeDetailBean.setTeachersCombo(filteredTeachers);
        return committeeDetailBean;
    }

    private CommitteeDetailBean mapTeacherByRule(Map<Integer, List<EmployeeProfileBean>> facultyWiseTeachers, Map<Integer, List<EmployeeCurrentDetailBean>> designationWiseTeachers,
                                                 Map<Long, EmployeeBean> teacherMap, Map<Integer, String> committeeRoles, GbltCommitteeDtl committeeDetail) {
        CommitteeDetailBean committeeDetailBean = BeanUtils.copyProperties(committeeDetail, CommitteeDetailBean.class);
        committeeDetailBean.setRoleName(committeeRoles.getOrDefault(committeeDetail.getUnumRoleId(), ""));
        Integer facultyId = committeeDetailBean.getUnumRoleCfacultyId();
        Integer departmentId = committeeDetailBean.getUnumRoleDepartmentId();
        Integer designationId = committeeDetailBean.getUnumRolePostId();
        Integer isExternal = committeeDetailBean.getUnumRoleIsExternal() == null ? 0 : committeeDetailBean.getUnumRoleIsExternal();
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
                if (teacherMap.containsKey(teacherId)) {
                    EmployeeBean employeeBean = teacherMap.get(teacherId);
                    Integer employeeRole = employeeBean.getUnumIsexternal() == null ? 0 : employeeBean.getUnumIsexternal();
                    if (isExternal.equals(employeeRole))
                        filteredTeachers.add(new ComboBean(employeeBean.getUnumEmpId().toString(), employeeBean.getUstrEmpName()));
                }
            }
        }
        committeeDetailBean.setTeachersCombo(filteredTeachers);
        return committeeDetailBean;
    }

    private boolean isDuplicateMember(Set<Long> memberIds, Long memberIdToCheck) {
        if (memberIdToCheck == null)
            return false;

        if (memberIds.contains(memberIdToCheck))
            return true;

        memberIds.add(memberIdToCheck);
        return false;
    }

    public ServiceResponse saveMemberMapping(CommitteeMemberBean committeeMemberBean) {
        int slNo = 1;
        Set<Long> members = new HashSet<>();
        for (CommitteeMember committeeMember: committeeMemberBean.getMembers()) {
            if (committeeMember.getUnumPreference1Empid() == null
                    && committeeMember.getUnumPreference2Empid() == null
                    && committeeMember.getUnumPreference3Empid() == null
                    && committeeMember.getUnumPreference4Empid() == null
                    && committeeMember.getUnumPreference5Empid() == null) {
                return ServiceResponse.errorResponse("No Employee selected for S.No. " + slNo);
            }
            if (isDuplicateMember(members, committeeMember.getUnumPreference1Empid()))
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected: " + committeeMember.getUnumPreference1Empname()));

            if (isDuplicateMember(members, committeeMember.getUnumPreference2Empid()))
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected: " + committeeMember.getUnumPreference2Empname()));

            if (isDuplicateMember(members, committeeMember.getUnumPreference3Empid()))
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected: " + committeeMember.getUnumPreference3Empname()));

            if (isDuplicateMember(members, committeeMember.getUnumPreference4Empid()))
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected: " + committeeMember.getUnumPreference4Empname()));

            if (isDuplicateMember(members, committeeMember.getUnumPreference5Empid()))
                return ServiceResponse.errorResponse(language.message("Duplicate Employee selected: " + committeeMember.getUnumPreference5Empname()));

            slNo++;
        }

        slNo = 1;
        List<GbltCommitteeMemberDtl> membersToSave = new ArrayList<>();
        List<Long> chairmen = new ArrayList<>();
        List<Long> member1 = new ArrayList<>();
        List<Long> member2 = new ArrayList<>();
        List<Long> committeeMembers = new ArrayList<>();
        boolean isMember1 = true;
        for (CommitteeMember committeeMember: committeeMemberBean.getMembers()) {
            GbltCommitteeMemberDtl committeeMemberDtl = BeanUtils.copyProperties(committeeMember, GbltCommitteeMemberDtl.class);
            if (committeeMember.getUnumPreference1Empid() == null) {
                committeeMemberDtl.setUnumPreference1Empname(null);
                committeeMembers.add(committeeMember.getUnumPreference1Empid());
            }
            if (committeeMember.getUnumPreference2Empid() == null) {
                committeeMemberDtl.setUnumPreference2Empname(null);
                committeeMembers.add(committeeMember.getUnumPreference2Empid());
            }
            if (committeeMember.getUnumPreference3Empid() == null) {
                committeeMemberDtl.setUnumPreference3Empname(null);
                committeeMembers.add(committeeMember.getUnumPreference3Empid());
            }
            if (committeeMember.getUnumPreference4Empid() == null) {
                committeeMemberDtl.setUnumPreference4Empname(null);
                committeeMembers.add(committeeMember.getUnumPreference4Empid());
            }
            if (committeeMember.getUnumPreference5Empid() == null) {
                committeeMemberDtl.setUnumPreference5Empname(null);
                committeeMembers.add(committeeMember.getUnumPreference5Empid());
            }

            committeeMemberDtl.setUnumComMemberId(committeeMemberMappingRepository.getNextId());
            committeeMemberDtl.setUnumIsvalid(committeeMemberBean.getUnumIsvalid());
            committeeMemberDtl.setUdtEntryDate(committeeMemberBean.getUdtEntryDate());
            committeeMemberDtl.setUnumComId(committeeMemberBean.getUnumComId());
            committeeMemberDtl.setUnumEntryUid(committeeMemberBean.getUnumEntryUid());
            committeeMemberDtl.setUnumEventid(committeeMemberBean.getUnumEventid());
            committeeMemberDtl.setUnumMemberSno(slNo++);
            committeeMemberDtl.setUnumUnivId(committeeMemberBean.getUnumUnivId());

            List<Long> employees = null;
            if (committeeMember.getUnumRoleId().equals(1)) {
                employees = chairmen;
            } else if (committeeMember.getUnumRoleId().equals(2) || committeeMember.getUnumRoleId().equals(3)) {
                if (isMember1) {
                    employees = member1;
                    isMember1 = false;
                } else {
                    employees = member2;
                }
            }
            if (employees != null) {
                addMember(employees, committeeMember.getUnumPreference1Empid());
                addMember(employees, committeeMember.getUnumPreference2Empid());
                addMember(employees, committeeMember.getUnumPreference3Empid());
                addMember(employees, committeeMember.getUnumPreference4Empid());
                addMember(employees, committeeMember.getUnumPreference5Empid());
            }

            membersToSave.add(committeeMemberDtl);
        }

        committeeMemberMappingRepository.saveAll(membersToSave);

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

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Committee Member Mapping"))
                .build();
    }

    private void addMember(List<Long> members, Long employeeId) {
        if (employeeId != null)
            members.add(employeeId);
    }

    public ServiceResponse getCommitteeMemberMappingByEventId(Long eventId) {
        List<GbltCommitteeMemberDtl> gbltCommitteeMemberDtlList = committeeMemberMappingRepository.findByUnumEventidAndUnumIsvalidAndUnumUnivId(eventId,1,RequestUtility.getUniversityId());

        if(gbltCommitteeMemberDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.message("Member Mapping not done"));
        }

        Optional<GbltCommitteeMst> committeeMstOptional = committeeMasterRepository.findByUnumIsvalidAndUnumComidAndUnumUnivId(1,gbltCommitteeMemberDtlList.get(0).getUnumComId(), RequestUtility.getUniversityId());

        if (committeeMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", gbltCommitteeMemberDtlList.get(0).getUnumComId()));
        }

        Map<Integer, String> committeeRoles = committeeRoleRepository.findAll().stream()
                .collect(Collectors.toMap(GmstCommitteeRoleMst::getUnumRoleId, GmstCommitteeRoleMst::getUstrRoleFname));

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

    public byte[] generateCommitteeReport(Long eventId) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        PdfPTable header = new PdfPTable(3);
        header.setWidthPercentage(100);
        header.setWidths(new int[] {1, 5, 1});
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA);

        document.open();
        PdfPCell hcell;
        // Logo
        hcell = new PdfPCell(new Phrase("", headerFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(hcell);

        // Header
        hcell = new PdfPCell(new Phrase(Constants.HeaderEnglish + "\n" + Constants.HeaderHindi, headerFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(hcell);

        // Logo
        hcell = new PdfPCell(new Phrase("", headerFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(hcell);

        document.add(header);

        document.close();
        System.out.println("Document Length " + baos.toByteArray().length);
        return baos.toByteArray();
    }
}
