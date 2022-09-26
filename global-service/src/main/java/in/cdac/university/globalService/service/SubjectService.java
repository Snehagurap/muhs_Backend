package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.repository.SubjectRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectBean> getAllSubjects(int status) throws Exception {
        return BeanUtils.copyListProperties(
                subjectRepository.listPageData(status, RequestUtility.getUniversityId()),
                SubjectBean.class
        );
    }
}
