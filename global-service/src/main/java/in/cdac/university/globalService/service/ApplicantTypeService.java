package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicantTypeBean;
import in.cdac.university.globalService.repository.ApplicantTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantTypeService {

    @Autowired
    private ApplicantTypeRepository applicantTypeRepository;

    public List<ApplicantTypeBean> getApplicantTypes() {
        return BeanUtils.copyListProperties(
                applicantTypeRepository.getActiveApplicantTypes(),
                ApplicantTypeBean.class
        );
    }
}
