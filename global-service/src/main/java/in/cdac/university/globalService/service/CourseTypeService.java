package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CourseTypeBean;
import in.cdac.university.globalService.repository.CourseTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
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
}
