package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DepartmentBean;
import in.cdac.university.globalService.repository.DepartmentRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentBean> departmentList(int isValid) {
        return BeanUtils.copyListProperties(
                departmentRepository.findByUnumIsvalidOrderByUstrDeptFname(isValid),
                DepartmentBean.class
        );
    }
}
