package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.StudentRelationshipBean;
import in.cdac.university.globalService.repository.StudentRelationshipRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentRelationshipService {

    @Autowired
    private StudentRelationshipRepository studentRelationshipRepository;

    public List<StudentRelationshipBean> getAllStuRelations() throws Exception {
        return BeanUtils.copyListProperties(
                studentRelationshipRepository.getAllStuRelations(RequestUtility.getUniversityId()),
                StudentRelationshipBean.class);
    }
}
