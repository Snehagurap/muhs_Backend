package in.cdac.university.globalService.service;

import com.google.common.hash.Hashing;
import in.cdac.university.globalService.bean.ApplicantBean;
import in.cdac.university.globalService.bean.ApplicantDetailBean;
import in.cdac.university.globalService.entity.GmstApplicantDraftMst;
import in.cdac.university.globalService.entity.GmstApplicantDtl;
import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.entity.GmstApplicantTypeMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ApplicantDetailRepository;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.ApplicantTypeRepository;
import in.cdac.university.globalService.repository.DraftApplicantRepository;
import in.cdac.university.globalService.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    private ApplicantTypeRepository applicantTypeRepository;

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
        Optional<GmstApplicantDraftMst> draftApplicantOptional = draftApplicantRepository.findByUnumIsvalidInAndUnumApplicantDraftid(List.of(1), applicantBean.getUnumApplicantDraftid());
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
            // Get the maximum applicant document id
            Long maxApplicantDocId = applicantDetailRepository.getMaxApplicantDocId(applicantId);

            for (GmstApplicantDtl gmstApplicantDtl : applicantDtls) {
                gmstApplicantDtl.setUnumApplicantId(applicantId);
                maxApplicantDocId++;
                gmstApplicantDtl.setUnumApplicantDocId(Long.valueOf(applicantId.toString() + "" + StringUtility.padLeftZeros(maxApplicantDocId.toString(), 5)));
            }
            applicantDetailRepository.saveAll(applicantDtls);
        }

        String message = language.saveSuccess("Applicant details");
        message += ". Your username is " + username + ", and password is " + password + ".";

        return ServiceResponse.successMessage(message);
    }

    public List<ApplicantBean> getListPageVerification(Integer isVerifiedApplicant) throws Exception {
        List<GmstApplicantMst> gmstApplicantMstList = applicantRepository.findByUnumIsVerifiedApplicantAndUnumIsvalid(isVerifiedApplicant, 1);
        if(gmstApplicantMstList.isEmpty()){
            return BeanUtils.copyListProperties(gmstApplicantMstList, ApplicantBean.class);
        }

        Map<Long, String> applicantTypes = applicantTypeRepository.findAll().stream()
                .collect(Collectors.toMap(GmstApplicantTypeMst::getUnumApplicantTypeId, GmstApplicantTypeMst::getUstrApplicantTypeFname));

        List<ApplicantBean> applicantBeanList = gmstApplicantMstList.stream().map(
                applicant -> {
                    ApplicantBean applicantBean = BeanUtils.copyProperties(applicant, ApplicantBean.class);
                    applicantBean.setUstrApplicantTypeName(applicantTypes.getOrDefault(applicant.getUnumApplicantTypeId(), ""));
                    return applicantBean;
                }
        ).toList();
        return BeanUtils.copyListProperties(applicantBeanList, ApplicantBean.class);
    }

    public ServiceResponse getApplicant(Long applicantId) {
        if(applicantId == null) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant id", applicantId));
        }
        Optional<GmstApplicantMst> gmstApplicantMst = applicantRepository.findByUnumApplicantIdAndUnumIsvalid(applicantId, 1);
        if(gmstApplicantMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant id", applicantId));
        }

        ApplicantBean applicantBean = BeanUtils.copyProperties(gmstApplicantMst.get(), ApplicantBean.class);

        List<GmstApplicantDtl> gmstApplicantDtlList = applicantDetailRepository.findByUnumApplicantIdAndUnumIsvalid(applicantId, 1);
        if(gmstApplicantDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant Document Detail", applicantId));
        }

        List<ApplicantDetailBean> applicantDetailBeanList = BeanUtils.copyListProperties(gmstApplicantDtlList, ApplicantDetailBean.class);

        applicantBean.setApplicantDetailBeans(applicantDetailBeanList);

        return ServiceResponse.successObject(applicantBean);
    }

    @Transactional
    public ServiceResponse verifyApplicant(ApplicantBean applicantBean) {
        if(applicantBean.getUnumApplicantId() == null) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant id", applicantBean.getUnumApplicantId()));
        }
        Long applicantId = applicantBean.getUnumApplicantId();

        Optional<GmstApplicantMst> gmstApplicantMst = applicantRepository.findByUnumApplicantIdAndUnumIsvalid(applicantId, 1);
        if(gmstApplicantMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant id", applicantId));
        }
        GmstApplicantMst gmstApplicantMst1 = gmstApplicantMst.get();
        gmstApplicantMst1.setUnumVerifiedBy(applicantBean.getUnumVerifiedBy());
        gmstApplicantMst1.setUdtVerifiedDate(applicantBean.getUdtVerifiedDate());
        gmstApplicantMst1.setUnumIsVerifiedApplicant(applicantBean.getUnumIsVerifiedApplicant());

        applicantRepository.save(gmstApplicantMst1);

        List<GmstApplicantDtl> gmstApplicantDtlList = applicantDetailRepository.findByUnumApplicantIdAndUnumIsvalid(applicantId, 1);
        if(gmstApplicantDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant Document Detail", applicantId));
        }

        gmstApplicantDtlList.forEach( applicantDetailBean -> {
                    applicantDetailBean.setUnumDocIsVerified(applicantBean.getUnumIsVerifiedApplicant());
        });

        applicantDetailRepository.saveAll(gmstApplicantDtlList);

        if(applicantBean.getUnumIsVerifiedApplicant() == -1) {
            return ServiceResponse.successMessage(language.message("Applicant Rejected"));
        }
        return ServiceResponse.successMessage(language.message("Applicant Verified"));
    }
}
