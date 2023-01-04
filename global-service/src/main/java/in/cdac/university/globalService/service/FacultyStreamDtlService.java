package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.FacultyStreamDtlBean;
import in.cdac.university.globalService.repository.FacultyStreamDtlRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyStreamDtlService {

    @Autowired
    FacultyStreamDtlRepository facultyStreamDtlRepository;

    public List<FacultyStreamDtlBean> getStreamComboByFaculty(Integer facultyId) throws Exception {
        return BeanUtils.copyListProperties(
                facultyStreamDtlRepository.findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(facultyId, 1, RequestUtility.getUniversityId()), FacultyStreamDtlBean.class
        );
    }
}
