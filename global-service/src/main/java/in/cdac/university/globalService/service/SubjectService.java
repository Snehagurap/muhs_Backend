package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.entity.GmstSubjectMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.SubjectRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private Language language;

    public List<SubjectBean> getAllSubjects(int status) throws Exception {
        return BeanUtils.copyListProperties(
                subjectRepository.listPageData(status, RequestUtility.getUniversityId()),
                SubjectBean.class
        );
    }

    @Transactional
    public ServiceResponse save(SubjectBean subjectBean) {
        // Duplicate check
        List<GmstSubjectMst> subjectList = subjectRepository.findByUnumIsvalidInAndUstrSubFnameIgnoreCase(
                List.of(1, 2), subjectBean.getUstrSubFname());
        if (!subjectList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Subject", subjectBean.getUstrSubFname()));
        }

        GmstSubjectMst subjectMst = BeanUtils.copyProperties(subjectBean, GmstSubjectMst.class);
        // Generate New Id
        Long subjectId = subjectRepository.getNextId();
        subjectMst.setUnumSubId(subjectId);
        subjectMst.setUnumSubCategoryId(1);
        subjectMst.setUnumSubtypeId(1);
        subjectRepository.save(subjectMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Subject"))
                .build();
    }

    public ServiceResponse getSubject(Long subjectId) throws Exception {

        Optional<GmstSubjectMst> subjectMstOptional = subjectRepository.findByUnumSubIdAndUnumUnivIdAndUnumIsvalidIn(
                subjectId, RequestUtility.getUniversityId(), List.of(1, 2)
        );

        if (subjectMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(
                    language.notFoundForId("Subject", subjectId)
            );
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(subjectMstOptional.get(), SubjectBean.class))
                .build();
    }

    @Transactional
    public ServiceResponse update(SubjectBean subjectBean) {
        if (subjectBean.getUnumSubId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Subject Id"));
        }

        // Duplicate check
        List<GmstSubjectMst> subjectMstList = subjectRepository.findByUnumIsvalidInAndUstrSubFnameIgnoreCaseAndUnumSubIdNot(
                List.of(1, 2), subjectBean.getUstrSubFname(), subjectBean.getUnumSubId());
        if (!subjectMstList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Subject", subjectBean.getUstrSubFname()));
        }

        // Create log
        int noOfRowsAffected = subjectRepository.createLog(List.of(subjectBean.getUnumSubId()));
        if (noOfRowsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Subject", subjectBean.getUnumSubId()));
        }

        // Save new data
        subjectBean.setUnumSubCategoryId(1);
        subjectBean.setUnumSubtypeId(1);
        GmstSubjectMst subjectMst = BeanUtils.copyProperties(subjectBean, GmstSubjectMst.class);
        subjectRepository.save(subjectMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Subject"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(SubjectBean subjectBean) {
        String[] idsToDelete = subjectBean.getIdsToDelete();
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Subject Id"));
        }

        List<Long> subjectIdsToDelete = Arrays.stream(idsToDelete).map(Long::valueOf).toList();

        List<GmstSubjectMst> subjectListToDelete = subjectRepository.findByUnumSubIdInAndUnumIsvalidInAndUnumUnivId(
                subjectIdsToDelete, List.of(1, 2), subjectBean.getUnumUnivId()
        );

        if (subjectListToDelete.size() != subjectIdsToDelete.size()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Subject", subjectIdsToDelete));
        }

        // Create Log
        int noOfRowsAffected = subjectRepository.createLog(subjectIdsToDelete);
        if (noOfRowsAffected != subjectIdsToDelete.size()) {
            throw new ApplicationException(language.deleteError("Subject"));
        }

        subjectListToDelete.forEach(subject -> {
            subject.setUnumIsvalid(0);
            subject.setUdtEntryDate(subjectBean.getUdtEntryDate());
            subject.setUnumEntryUid(subject.getUnumEntryUid());
        });

        subjectRepository.saveAll(subjectListToDelete);

        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Subject"))
                .build();
    }
}
