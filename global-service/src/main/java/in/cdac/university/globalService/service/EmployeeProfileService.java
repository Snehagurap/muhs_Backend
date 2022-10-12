package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.EmployeeProfileBean;
import in.cdac.university.globalService.repository.EmployeeProfileRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProfileService {

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    public List<EmployeeProfileBean> getTeachersProfilesByFaculty(List<Integer> facultyId) throws Exception {
        return BeanUtils.copyListProperties(
                employeeProfileRepository.findByUnumIsvalidAndUnumUnivIdAndUnumFacultyIdIn(1, RequestUtility.getUniversityId(), facultyId),
                EmployeeProfileBean.class
        );
    }
}
