package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private Language language;

    @Transactional
    public ServiceResponse save(ApplicantBean applicantBean) {

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Applicant details"))
                .build();
    }
}
