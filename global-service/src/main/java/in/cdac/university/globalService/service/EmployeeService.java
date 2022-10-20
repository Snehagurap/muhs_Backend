package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CommitteeBean;
import in.cdac.university.globalService.bean.EventBean;
import in.cdac.university.globalService.bean.EmployeeBean;
import in.cdac.university.globalService.entity.GmstEmpMst;
import in.cdac.university.globalService.repository.EmployeeRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(eventBean);

        CommitteeBean committeeBean = restUtility.get(RestUtility.SERVICE_TYPE.COMMITTEE, Constants.URL_GET_COMMITTEE_BY_ID + eventBean.getUnumComid(), CommitteeBean.class);
        System.out.println(committeeBean);

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
}
