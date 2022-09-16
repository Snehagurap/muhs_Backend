package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.LanguageBean;
import in.cdac.university.globalService.repository.LanguageRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<LanguageBean> getAllLanguages() throws Exception {
        return BeanUtils.copyListProperties(
                languageRepository.getAllLanguages(RequestUtility.getUniversityId()),
                LanguageBean.class
        );
    }
}
