package in.cdac.university.globalService.service;


import in.cdac.university.globalService.bean.DesignationBean;
import in.cdac.university.globalService.entity.GmstPostMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.DesignationRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private Language language;

    public List<DesignationBean> getAllDesignations() {
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

    public ServiceResponse getDesignation(Integer postId) throws Exception {
        Optional<GmstPostMst> postMstOptional = designationRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumPostId(
                List.of(1, 2), RequestUtility.getUniversityId(), postId
        );

        if (postMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Designation", postId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(postMstOptional.get(), DesignationBean.class))
                .build();
    }

    public List<DesignationBean> listPageData(int status) throws Exception {
        return BeanUtils.copyListProperties(
                designationRepository.listPageData(status, RequestUtility.getUniversityId()),
                DesignationBean.class
        );
    }

    @Transactional
    public ServiceResponse save(DesignationBean designationBean) {
        //for duplicate
        List<GmstPostMst> designationList = designationRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrPostFnameIgnoreCase(
                List.of(1, 2), designationBean.getUnumUnivId(), designationBean.getUstrPostFname()
        );
        if (!designationList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Designation", designationBean.getUstrPostFname()));
        }
        GmstPostMst gmstPostMst = BeanUtils.copyProperties(designationBean, GmstPostMst.class);
        Integer postId = designationRepository.getNextId();
        gmstPostMst.setUnumPostId(postId);
        designationRepository.save(gmstPostMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Designation"))
                .build();
    }

    @Transactional
    public ServiceResponse update(DesignationBean designationBean) {
        if (designationBean.getUnumPostId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Post Id")));
        }

        // Duplicate Check
        List<GmstPostMst> postMsts = designationRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrPostFnameIgnoreCaseAndUnumPostIdNot(
                List.of(1, 2), designationBean.getUnumUnivId(), designationBean.getUstrPostFname(), designationBean.getUnumPostId()
        );

        if (!postMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Designation", designationBean.getUstrPostFname()));
        }

        // Create Log
        int noOfRecordsAffected = designationRepository.createLog(List.of((designationBean.getUnumPostId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Designation", designationBean.getUnumPostId()));
        }

        // Save new Data
        GmstPostMst postMst = BeanUtils.copyProperties(designationBean, GmstPostMst.class);
        designationRepository.save(postMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Designation"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(DesignationBean designationBean, Integer[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Post Id"));
        }

        List<GmstPostMst> postMsts = designationRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumPostIdIn(
                List.of(1, 2), designationBean.getUnumUnivId(), List.of(idsToDelete)
        );

        if (postMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Designation", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = designationRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Designation"));
        }

        postMsts.forEach(post -> {
            post.setUnumIsvalid(0);
            post.setUdtEntryDate(designationBean.getUdtEntryDate());
        });

        designationRepository.saveAll(postMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Designation"))
                .build();
    }
}
