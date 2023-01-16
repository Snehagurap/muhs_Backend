package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.FacultyBean;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public List<FacultyBean> getAllFaculty() throws Exception {
        Integer universityId = RequestUtility.getUniversityId();
        return BeanUtils.copyListProperties(
                facultyRepository.getAllFaculty(universityId),
                FacultyBean.class
        );
    }

    public ServiceResponse allFaculties() {
        return ServiceResponse.successObject(
                BeanUtils.copyListProperties(
                        facultyRepository.findByUnumIsvalid(1),
                        FacultyBean.class
                )
        );
    }
}
