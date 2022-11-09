package in.cdac.university.globalService.service;

import com.google.common.hash.Hashing;
import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.bean.ApplicantDetailBean;
import in.cdac.university.globalService.entity.GmstApplicantDraftMst;
import in.cdac.university.globalService.entity.GmstApplicantDtl;
import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ApplicantDetailRepository;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.DraftApplicantRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private DraftApplicantRepository draftApplicantRepository;

    @Autowired
    private ApplicantDetailRepository applicantDetailRepository;

    @Autowired
    private Language language;

    @Autowired
    private FtpUtility ftpUtility;

    @Transactional
    public ServiceResponse saveApplicantDetails(ApplicantBean applicantBean) {
        // Upload Documents
        List<ApplicantDetailBean> applicantDetailBeans = applicantBean.getApplicantDetailBeans();
        List<GmstApplicantDtl> applicantDtls = new ArrayList<>();
        if (applicantDetailBeans != null && !applicantDetailBeans.isEmpty()) {
            for (ApplicantDetailBean applicantDetailBean: applicantDetailBeans) {
                if (applicantDetailBean.getUstrDocPath() != null && !applicantDetailBean.getUstrDocPath().isBlank()) {
                    boolean isFileMoved = ftpUtility.moveFileFromTempToFinalDirectory(applicantDetailBean.getUstrDocPath());
                    if (!isFileMoved) {
                        throw new ApplicationException("Unable to upload file");
                    }
                    GmstApplicantDtl gmstApplicantDtl = BeanUtils.copyProperties(applicantDetailBean, GmstApplicantDtl.class);
                    gmstApplicantDtl.setUnumIsvalid(applicantBean.getUnumIsvalid());
                    gmstApplicantDtl.setUdtEffFrom(applicantBean.getUdtEffFrom());
                    gmstApplicantDtl.setUdtEntryDate(applicantBean.getUdtEntryDate());
                    gmstApplicantDtl.setUnumDocIsVerified(0);
                    applicantDtls.add(gmstApplicantDtl);
                }
            }
        }

        // Get Draft application details
        Optional<GmstApplicantDraftMst> draftApplicantOptional = draftApplicantRepository.findByUnumIsvalidAndUnumApplicantDraftid(1, applicantBean.getUnumApplicantDraftid());
        if (draftApplicantOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant", applicantBean.getUnumApplicantDraftid()));

        // Update draft applicant mst
        draftApplicantRepository.updateDraftApplicantIsValid(applicantBean.getUnumApplicantDraftid(), 0);

        Long applicantId = applicantRepository.getNextId();
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

        // Save Document Details
        if (!applicantDtls.isEmpty()) {
            applicantDtls.forEach(gmstApplicantDtl -> {
                gmstApplicantDtl.setUnumApplicantId(applicantId);
            });
            applicantDetailRepository.saveAll(applicantDtls);
        }

        String message = language.saveSuccess("Applicant details");
        message += ". Your username is " + username + ", and password is " + password + ".";

        return ServiceResponse.successMessage(message);
    }
}
