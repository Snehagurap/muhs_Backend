package in.cdac.university.globalService.service;



import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.*;
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

        //for duplicate
        List<GmstEmpMst> empMstList = employeeRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrTPanNo(
                List.of(1, 2), employeeBean.getUnumUnivId(), employeeBean.getUstrTPanNo()
        );
        if (!empMstList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Employee", employeeBean.getUstrTPanNo()));
        }
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
            throw new ApplicationException(language.notFoundForId("Teacher ID ", employeeBean.getUnumEmpId()));
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
        Long empID = empMst.getUnumEmpId();
//        Long empID = employeeRepository.getNextId();
//        empMst.setUnumEmpId(empID);
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

        // Create Log Emp Mst
        int noOfRowsAffected = employeeRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Teacher"));
        }

        List<GmstEmpCurDtl> gmstEmpCurDtlList = employeeCurrentDetailRepository.findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete),  List.of(1, 2), employeeBean.getUnumUnivId()
        );
        System.out.println("gmstEmpCurDtlList>>"+gmstEmpCurDtlList.size());
        if(gmstEmpCurDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Teacher", Arrays.toString(idsToDelete)));
        }

        // create log Current Detail
        int noOfRowsAffectedCurDtl = employeeCurrentDetailRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffectedCurDtl != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Teacher"));
        }

        List<GmstEmpProfileDtl> gmstEmpProfileDtlList = employeeProfileRepository.findByUnumEmpIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete),  List.of(1, 2), employeeBean.getUnumUnivId()
        );

        if(gmstEmpProfileDtlList.isEmpty()) {
            throw new ApplicationException(language.deleteError("Teacher"));
        }

        // create log Profile
        employeeProfileRepository.createLog(List.of(idsToDelete));

        empMstList.forEach(teacher -> {
            teacher.setUnumIsvalid(0);
            teacher.setUdtEntryDate(employeeBean.getUdtEntryDate());
            teacher.setUnumEntryUid(employeeBean.getUnumEntryUid());
        });

        gmstEmpCurDtlList.forEach(teacher -> {
            teacher.setUnumIsvalid(0);
            teacher.setUdtEntryDate(employeeBean.getUdtEntryDate());
            teacher.setUnumEntryUid(employeeBean.getUnumEntryUid());
        });

        gmstEmpProfileDtlList.forEach(teacher -> {
            teacher.setUnumIsvalid(0);
            teacher.setUdtEntryDate(employeeBean.getUdtEntryDate());
            teacher.setUnumEntryUid(employeeBean.getUnumEntryUid());
        });

        employeeRepository.saveAll(empMstList);
        employeeCurrentDetailRepository.saveAll(gmstEmpCurDtlList);
        employeeProfileRepository.saveAll(gmstEmpProfileDtlList);
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

    @Transactional
    public ServiceResponse saveCollegeTeachersDtls(List<EmployeeBean> employeeBeanList) throws Exception {

            Long maxEmpId = 0L;
            GmstEmpMst gmstEmpMst;
            List<GmstEmpMst> gmstEmpMstList = new ArrayList<>();
            if(employeeBeanList != null && employeeBeanList.size() > 0){


                for(EmployeeBean employeeBean1 : employeeBeanList) {
                    //for duplicate
                    List<GmstEmpMst> empMstList = employeeRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrTPanNo(
                            List.of(1, 2), employeeBean1.getUnumUnivId(), employeeBean1.getUstrTPanNo()
                    );
                    if (!empMstList.isEmpty()) {
                        return ServiceResponse.errorResponse(language.duplicate("Employee", employeeBean1.getUstrTPanNo()));
                    }
                    maxEmpId  = employeeRepository.getNextId();
                    gmstEmpMst = BeanUtils.copyProperties(employeeBean1, GmstEmpMst.class);
                    gmstEmpMst.setUnumEmpId(maxEmpId);
                    gmstEmpMst.setUdtEntryDate(new Date());
                    gmstEmpMst.setUdtEffFrom(new Date());
                    gmstEmpMst.setUnumEntryUid(RequestUtility.getUserId());
                    gmstEmpMst.setUnumUnivId(RequestUtility.getUniversityId());
                    gmstEmpMst.setUnumIsvalid(1);
                    gmstEmpMstList.add(gmstEmpMst);

                    GmstEmpProfileDtl gmstEmpProfileDtl = new GmstEmpProfileDtl();
                    EmployeeProfileBean employeeProfileBean = employeeBean1.getEmployeeProfileBean();
                    if (employeeProfileBean != null) {
                        Long maxEmpProfileId = employeeProfileRepository.getMaxEmpProfileId(maxEmpId);
                        gmstEmpProfileDtl =  BeanUtils.copyProperties(employeeProfileBean, GmstEmpProfileDtl.class);
                            gmstEmpProfileDtl.setUnumEmpId(maxEmpId);
                            maxEmpProfileId++;
                            gmstEmpProfileDtl.setUnumProfileId(Long.valueOf(maxEmpId.toString() + "" + StringUtility.padLeftZeros(maxEmpProfileId.toString(), 5)));
                            gmstEmpProfileDtl.setUnumCollegeId(employeeProfileBean.getUnumCollegeId());
                            gmstEmpProfileDtl.setUnumIsvalid(1);
                            gmstEmpProfileDtl.setUdtEntryDate(new Date());
                            gmstEmpProfileDtl.setUnumUnivId(RequestUtility.getUniversityId());
                            gmstEmpProfileDtl.setUnumEntryUid(RequestUtility.getUserId());
                            gmstEmpProfileDtl.setUdtEffFrom(new Date());

                        }



                    GmstEmpCurDtl gmstEmpCurDtl = new GmstEmpCurDtl();
                    EmployeeCurrentDetailBean employeeCurrentDetailBean = employeeBean1.getEmployeeCurrentDetailBean();
                    if(employeeCurrentDetailBean != null){
                            gmstEmpCurDtl = BeanUtils.copyProperties(employeeCurrentDetailBean, GmstEmpCurDtl.class);
                            gmstEmpCurDtl.setUnumEmpId(maxEmpId);
                            gmstEmpCurDtl.setUstrTAadharNo(employeeCurrentDetailBean.getUstrTAadharNo());
                            gmstEmpCurDtl.setUnumIsvalid(1);
                            gmstEmpCurDtl.setUdtEntryDate(new Date());
                            gmstEmpCurDtl.setUnumUnivId(RequestUtility.getUniversityId());
                            gmstEmpCurDtl.setUnumEntryUid(RequestUtility.getUserId());
                            Long maxEmpCurrDetailId = employeeCurrentDetailRepository.getMaxEmpCurrDetailId(maxEmpId);
                            maxEmpCurrDetailId++;
                            gmstEmpCurDtl.setUnumEmpCurId(Long.valueOf(maxEmpId.toString() + "" + StringUtility.padLeftZeros(maxEmpCurrDetailId.toString(), 5)));
                        }

                        employeeCurrentDetailRepository.save(gmstEmpCurDtl);
                        employeeProfileRepository.save(gmstEmpProfileDtl);

                }
                employeeRepository.saveAll(gmstEmpMstList);
            }

        return ServiceResponse.successMessage(language.saveSuccess("College teachers details"));
    }

    @Transactional
    public List<EmployeeBean> getTeacherDetailsByCollegeId(Long collegeId) {
        List<GmstEmpProfileDtl> gmstEmpProfileDtlList = employeeProfileRepository.findAllByUnumCollegeIdAndUnumIsvalid(collegeId,1);
        List<EmployeeBean> employeeBeanList = new ArrayList<>();

         if(!gmstEmpProfileDtlList.isEmpty()){

             for(GmstEmpProfileDtl gmstEmpProfileDtl : gmstEmpProfileDtlList){
                 if(gmstEmpProfileDtl != null){
                 EmployeeProfileBean employeeProfileBean = BeanUtils.copyProperties(gmstEmpProfileDtl, EmployeeProfileBean.class);

                     GmstEmpMst gmstEmpMst = employeeRepository.findByUnumEmpIdAndUnumIsvalidAndUnumUnivId(employeeProfileBean.getUnumEmpId()
                             ,employeeProfileBean.getUnumIsvalid(),employeeProfileBean.getUnumUnivId());
                     if(gmstEmpMst != null){
                         EmployeeBean employeeBean = BeanUtils.copyProperties(gmstEmpMst, EmployeeBean.class);
                         GmstEmpCurDtl gmstEmpCurDtl = employeeCurrentDetailRepository.findByUnumEmpIdAndUnumIsvalidAndUnumUnivId(employeeBean.getUnumEmpId(),
                                 employeeBean.getUnumIsvalid(),employeeBean.getUnumUnivId());
                         EmployeeCurrentDetailBean employeeCurrentDetailBean = BeanUtils.copyProperties(gmstEmpCurDtl, EmployeeCurrentDetailBean.class);
                         employeeBean.setEmployeeProfileBean(employeeProfileBean);
                         employeeBean.setEmployeeCurrentDetailBean(employeeCurrentDetailBean);
                         employeeBean.setIsDisabled(true);
                         employeeBeanList.add(employeeBean);
                     }

                 }
             }

         }
     return employeeBeanList;
    }

    @Transactional
    public ServiceResponse updateTeacherByCollegeId(EmployeeBean employeeBean) throws Exception {

        if (employeeBean.getUnumEmpId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Employee Id"));
        }


        //create log
        int noOfEmpAffected = employeeRepository.createLog(List.of(employeeBean.getUnumEmpId()));
            if (noOfEmpAffected == 0) {
                throw new ApplicationException(language.notFoundForId("Teacher Id", employeeBean.getUnumEmpId()));
            }
            GmstEmpMst gmstEmpMst = BeanUtils.copyProperties(employeeBean, GmstEmpMst.class);
            employeeRepository.saveAndFlush(gmstEmpMst);

        int noOfEmpProfileAffected = employeeProfileRepository.createLog(List.of(employeeBean.getUnumEmpId()));
            if (noOfEmpProfileAffected == 0) {
                throw new ApplicationException(language.updateError("Teacher"));
            }
            //save record
            employeeBean.getEmployeeProfileBean().setUdtEntryDate(new Date());
            employeeBean.getEmployeeProfileBean().setUnumIsvalid(1);
            employeeBean.getEmployeeProfileBean().setUnumEntryUid(RequestUtility.getUserId());
            employeeBean.getEmployeeProfileBean().setUnumUnivId(RequestUtility.getUniversityId());
            employeeBean.getEmployeeProfileBean().setUnumEmpId(employeeBean.getUnumEmpId());
            employeeBean.setEmployeeProfileBean(employeeBean.getEmployeeProfileBean());

            GmstEmpProfileDtl gmstEmpProfileDtl = BeanUtils.copyProperties(employeeBean.getEmployeeProfileBean(), GmstEmpProfileDtl.class);
            employeeProfileRepository.saveAndFlush(gmstEmpProfileDtl);
        int noOfEmpCurrAffected = employeeCurrentDetailRepository.createLog(List.of(employeeBean.getUnumEmpId()));
            if (noOfEmpCurrAffected == 0) {
                throw new ApplicationException(language.updateError("Teacher"));
            }

            employeeBean.getEmployeeCurrentDetailBean().setUdtEntryDate(new Date());
            employeeBean.getEmployeeCurrentDetailBean().setUnumIsvalid(1);
            employeeBean.getEmployeeCurrentDetailBean().setUnumEntryUid(RequestUtility.getUserId());
            employeeBean.getEmployeeCurrentDetailBean().setUnumUnivId(RequestUtility.getUniversityId());
            employeeBean.getEmployeeCurrentDetailBean().setUnumEmpId(employeeBean.getUnumEmpId());
            employeeBean.setEmployeeCurrentDetailBean(employeeBean.getEmployeeCurrentDetailBean());

            GmstEmpCurDtl gmstEmpCurDtl = BeanUtils.copyProperties(employeeBean.getEmployeeCurrentDetailBean(), GmstEmpCurDtl.class);

           employeeCurrentDetailRepository.saveAndFlush(gmstEmpCurDtl);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Teacher Details"))
                .build();
    }

}
