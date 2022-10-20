package in.cdac.university.globalService.service;

import com.google.common.hash.Hashing;
import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.bean.DraftApplicantBean;
import in.cdac.university.globalService.entity.GmstApplicantDraftMst;
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
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DraftApplicantService {

    @Autowired
    private DraftApplicantRepository draftApplicantRepository;

    @Autowired
    private Language language;

    @Transactional
    public ServiceResponse save(DraftApplicantBean draftApplicantBean) {
        if (draftApplicantBean.getIgnoreDuplicate() == 0) {
            List<GmstApplicantDraftMst> applicantsWithMobileAndEmail = draftApplicantRepository.findByUnumIsvalidAndUnumApplicantMobileAndUstrApplicantEmail(
                    1, draftApplicantBean.getUnumApplicantMobile(), draftApplicantBean.getUstrApplicantEmail());

            if (!applicantsWithMobileAndEmail.isEmpty()) {
                return ServiceResponse.builder()
                        .status(2)
                        .message("Email and Mobile no. already exists. Do you still want to save?")
                        .build();
            }
        }

        GmstApplicantDraftMst applicantDraftMst = BeanUtils.copyProperties(draftApplicantBean, GmstApplicantDraftMst.class);

        // Generate OTP
        String emailOtp = RandomStringUtils.randomNumeric(6);
        String mobileOtp = RandomStringUtils.randomNumeric(6);

        applicantDraftMst.setUnumApplicantDraftid(draftApplicantRepository.getNextId());
        applicantDraftMst.setUstrGeneratedEmailotp(emailOtp);
        applicantDraftMst.setUstrGeneratedMotp(mobileOtp);
        draftApplicantRepository.save(applicantDraftMst);

        String successMessage = language.saveSuccess("Applicant Details") + ". Your draft applicant Id is "
                + applicantDraftMst.getUnumApplicantDraftid() + ". Please keep the same for future reference."
                + " Mobile OTP: " + mobileOtp
                + " and Email OTP: " + emailOtp;

        return ServiceResponse.builder()
                .status(1)
                .message(successMessage)
                .responseObject(applicantDraftMst)
                .build();
    }

    @Transactional
    public ServiceResponse validateOtp(DraftApplicantBean draftApplicantBean) {
        if (draftApplicantBean.getUnumApplicantDraftid() == null ||
                draftApplicantBean.getUstrGeneratedEmailotp() == null ||
                draftApplicantBean.getUstrGeneratedMotp() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Applicant Id, Mobile OTP, Email OTP"));
        }

        // Validate Mobile and email OTP
        Optional<GmstApplicantDraftMst> draftApplicant = draftApplicantRepository.findByUnumIsvalidAndUnumApplicantDraftid(2, draftApplicantBean.getUnumApplicantDraftid());
        if (draftApplicant.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant", draftApplicantBean.getUnumApplicantDraftid()));
        }

        GmstApplicantDraftMst applicant = draftApplicant.get();
        if (!draftApplicantBean.getUstrGeneratedMotp().equals(applicant.getUstrGeneratedMotp()) ||
                !draftApplicantBean.getUstrGeneratedEmailotp().equals(applicant.getUstrGeneratedEmailotp())) {
            return ServiceResponse.errorResponse(language.message("Invalid Email or Mobile OTP"));
        }

        // Generate new username and password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = RandomStringUtils.randomAlphanumeric(8);
        log.debug("Generated Password: {}", password);

        String encodedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        encodedPassword = encoder.encode(encodedPassword);

        String username = "applicant_" + applicant.getUnumApplicantDraftid();
        int noOfRowsAffected = draftApplicantRepository.updateDraftApplicantUsernameAndPassword(
                username, password, encodedPassword, applicant.getUnumApplicantDraftid()
        );

        if (noOfRowsAffected == 0)
            return ServiceResponse.errorResponse(language.message("Unable to validate OTP."));

        return ServiceResponse.builder()
                .status(1)
                .message(language.message("OTP validated successfully. " +
                        "Your Username is " + username + ", and Password is " + password + ". Keep the same for future reference."))
                .build();
    }

    public ServiceResponse getApplicantById(Long applicantId) {
        Optional<GmstApplicantDraftMst> applicantDraftMstOptional = draftApplicantRepository.findByUnumIsvalidAndUnumApplicantDraftid(1, applicantId);
        if (applicantDraftMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant", applicantId));

        return ServiceResponse.successObject(
                BeanUtils.copyProperties(applicantDraftMstOptional.get(), DraftApplicantBean.class)
        );
    }
}
