package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.entity.GmstCollegeMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.CollegeRepository;
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
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private Language language;

    public List<CollegeBean> listPageData(int isValid) throws Exception {
        int universityId = RequestUtility.getUniversityId();
        return BeanUtils.copyListProperties(
                collegeRepository.listPageData(isValid, universityId),
                CollegeBean.class
        );
    }

    public List<CollegeBean> getColleges() throws Exception {
        int universityId = RequestUtility.getUniversityId();
        return BeanUtils.copyListProperties(
                collegeRepository.findColleges(universityId),
                CollegeBean.class
        );
    }

    public ServiceResponse getCollegeById(Long collegeId) throws Exception {

        Optional<GmstCollegeMst> collegeMstOptional = collegeRepository.findByUnumCollegeIdAndUnumIsvalidInAndUnumUnivId(
                collegeId, List.of(1, 2), RequestUtility.getUniversityId()
        );

        if (collegeMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("College", collegeId));

        CollegeBean collegeBean = BeanUtils.copyProperties(collegeMstOptional.get(), CollegeBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(collegeBean)
                .build();
    }

    @Transactional
    public ServiceResponse save(CollegeBean collegeBean) {
        // Duplicate check
        List<GmstCollegeMst> collegeMsts = collegeRepository.findByUnumIsvalidInAndUstrColFnameIgnoreCaseAndUnumUnivId(
                List.of(1, 2), collegeBean.getUstrColFname(), collegeBean.getUnumUnivId()
        );

        if (!collegeMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("College", collegeBean.getUstrColFname()));
        }

        // Generate new college id
        GmstCollegeMst collegeMst = BeanUtils.copyProperties(collegeBean, GmstCollegeMst.class);
        Long collegeId = collegeRepository.getNextId();
        collegeMst.setUnumCollegeId(collegeId);
        collegeRepository.save(collegeMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("College"))
                .build();
    }

    @Transactional
    public ServiceResponse update(CollegeBean collegeBean) {
        if (collegeBean.getUnumCollegeId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("College Id"));
        }

        // Duplicate check
        List<GmstCollegeMst> collegeMsts = collegeRepository.findByUnumIsvalidInAndUstrColFnameIgnoreCaseAndUnumUnivIdAndUnumCollegeIdNot(
                List.of(1, 2), collegeBean.getUstrColFname(), collegeBean.getUnumUnivId(), collegeBean.getUnumCollegeId()
        );

        if (!collegeMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("College", collegeBean.getUstrColFname()));
        }

        // Create Log
        int noOfRecordsAffected = collegeRepository.createLog(List.of(collegeBean.getUnumCollegeId()));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("College", collegeBean.getUnumCollegeId()));
        }

        // Save new Data
        GmstCollegeMst collegeMst = BeanUtils.copyProperties(collegeBean, GmstCollegeMst.class);
        collegeRepository.save(collegeMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("College"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(CollegeBean collegeBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("College Id"));
        }

        List<GmstCollegeMst> collegeMstList = collegeRepository.findByUnumCollegeIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete), List.of(1, 2), collegeBean.getUnumUnivId()
        );

        if (collegeMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("College", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = collegeRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("College"));
        }

        collegeMstList.forEach(college -> {
            college.setUnumIsvalid(0);
            college.setUdtEntryDate(collegeBean.getUdtEntryDate());
            college.setUnumEntryUid(collegeBean.getUnumEntryUid());
        });

        collegeRepository.saveAll(collegeMstList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("College"))
                .build();
    }
}
