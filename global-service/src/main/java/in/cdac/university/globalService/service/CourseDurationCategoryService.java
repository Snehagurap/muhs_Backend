package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CourseDurationCategoryBean;
import in.cdac.university.globalService.repository.CourseDurationCategoryRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDurationCategoryService {

    @Autowired
    private CourseDurationCategoryRepository courseDurationCategoryRepository;

    public List<CourseDurationCategoryBean> courseDurationCategories() throws Exception {
        return BeanUtils.copyListProperties(
                courseDurationCategoryRepository.allCourseDurationCategories(RequestUtility.getUniversityId()),
                CourseDurationCategoryBean.class
        );
    }
}
