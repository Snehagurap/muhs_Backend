package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.entity.GmstCollegeMst;
import in.cdac.university.globalService.entity.GmstCollegeMstPK;
import in.cdac.university.globalService.repository.CollegeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ServiceResponse getCollegeById(Long collegeId) {

        Optional<GmstCollegeMst> collegeMstOptional = collegeRepository.findById(new GmstCollegeMstPK(collegeId, 1));

        if (collegeMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("College", collegeId));

        CollegeBean collegeBean = BeanUtils.copyProperties(collegeMstOptional.get(), CollegeBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(collegeBean)
                .build();
    }
}
