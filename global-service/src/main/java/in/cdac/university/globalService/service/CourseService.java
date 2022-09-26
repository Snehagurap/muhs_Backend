package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CourseBean;
import in.cdac.university.globalService.repository.CourseRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseBean> listPageData(int status) throws Exception {
        return BeanUtils.copyListProperties(
                courseRepository.listPageData(status, RequestUtility.getUniversityId()),
                CourseBean.class
        );
    }
}
