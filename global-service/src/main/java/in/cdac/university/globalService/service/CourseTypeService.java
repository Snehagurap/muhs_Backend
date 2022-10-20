package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CourseTypeBean;
import in.cdac.university.globalService.controller.GmstCourseTypeMst;
import in.cdac.university.globalService.repository.CourseTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTypeService {

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    public List<CourseTypeBean> courseTypes() throws Exception {
        return BeanUtils.copyListProperties(
                courseTypeRepository.getAllCourseTypes(RequestUtility.getUniversityId()),
                CourseTypeBean.class
        );
    }

    public ServiceResponse getAllCourseTypes() {
        List<GmstCourseTypeMst> courseTypeMsts = courseTypeRepository.findByUnumIsvalid(1);
        return ServiceResponse.successObject(
                BeanUtils.copyListProperties(courseTypeMsts, CourseTypeBean.class)
        );
    }
}
