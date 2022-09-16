package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DesignationBean;
import in.cdac.university.globalService.repository.DesignationRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public List<DesignationBean> getAllDesignations() throws Exception {
        Integer universityId = null;
        try {
            universityId = RequestUtility.getUniversityId();
        } catch (Exception e) {
            log.info("Ignoring University ID for Designation");
        }
        if (universityId == null || universityId <= 0)
            universityId = 1;
        return BeanUtils.copyListProperties(
                designationRepository.getAllDesignations(universityId),
                DesignationBean.class);
    }
}
