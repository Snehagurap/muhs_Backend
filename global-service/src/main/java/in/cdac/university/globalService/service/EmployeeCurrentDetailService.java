package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.EmployeeCurrentDetailBean;
import in.cdac.university.globalService.repository.EmployeeCurrentDetailRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeCurrentDetailService {

    @Autowired
    private EmployeeCurrentDetailRepository employeeCurrentDetailRepository;

    public List<EmployeeCurrentDetailBean> getEmpCurrentDetailsByDesignation(List<Integer> designationIds) throws Exception {
        return BeanUtils.copyListProperties(
                employeeCurrentDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumEmpDesigidIn(1, RequestUtility.getUniversityId(), designationIds),
                EmployeeCurrentDetailBean.class
        );
    }
}
