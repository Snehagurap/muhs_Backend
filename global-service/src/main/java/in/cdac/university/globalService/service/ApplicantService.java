package in.cdac.university.globalService.service;

import com.google.common.hash.Hashing;
import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.entity.GmstApplicantDraftMst;
import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.DraftApplicantRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private DraftApplicantRepository draftApplicantRepository;

    @Autowired
    private Language language;

    @Transactional
    public ServiceResponse saveApplicantDetails(ApplicantBean applicantBean) {
        // Get Draft application details
        Optional<GmstApplicantDraftMst> draftApplicantOptional = draftApplicantRepository.findByUnumIsvalidAndUnumApplicantDraftid(1, applicantBean.getUnumApplicantDraftid());
        if (draftApplicantOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant", applicantBean.getUnumApplicantDraftid()));

        // Update draft applicant mst
        draftApplicantRepository.updateDraftApplicantIsValid(applicantBean.getUnumApplicantDraftid(), 0);

        Long applicantId = applicantRepository.getNextId();;
        GmstApplicantDraftMst draftApplicant = draftApplicantOptional.get();
        applicantBean.setUnumApplicantId(applicantId);
        applicantBean.setUdtEffFrom(new Date());
        applicantBean.setUnumApplicantDistrictid(draftApplicant.getUnumApplicantDistrictid());
        applicantBean.setUnumApplicantMobile(draftApplicant.getUnumApplicantMobile());
        applicantBean.setUnumApplicantPincode(draftApplicant.getUnumApplicantPincode());
        applicantBean.setUnumApplicantStateid(draftApplicant.getUnumApplicantStateid());
        applicantBean.setUnumIsVerifiedApplicant(0);
        applicantBean.setUstrApplicantAddress(draftApplicant.getUstrApplicantAddress());
        applicantBean.setUstrApplicantCity(draftApplicant.getUstrApplicantCity());
        applicantBean.setUstrApplicantEmail(draftApplicant.getUstrApplicantEmail());
        applicantBean.setUstrApplicantName(draftApplicant.getUstrApplicantName());

        // Generate new username and password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = RandomStringUtils.randomAlphanumeric(8);
        String username = "muhs_" + applicantId;

        log.debug("Generated Password: {}", password);

        String encodedPassword = Hashing.sha256()
                .hashString(password + username, StandardCharsets.UTF_8)
                .toString();
        encodedPassword = encoder.encode(encodedPassword);
        applicantBean.setUstrUid(username);
        applicantBean.setUstrPass(encodedPassword);
        applicantBean.setUstrTempPass(password);

        GmstApplicantMst applicantMst = BeanUtils.copyProperties(applicantBean, GmstApplicantMst.class);
        applicantRepository.save(applicantMst);

        String message = language.saveSuccess("Applicant details");
        message += ". Your username is " + username + ", and password is " + password + ".";

        return ServiceResponse.builder()
                .status(1)
                .message(message)
                .build();
    }
}
