package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateComponentRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateComponentService {

    @Autowired
    private TemplateComponentRepository templateComponentRepository;

    @Autowired
    private TemplateComponentDetailRepository templateComponentDetailRepository;

    public List<TemplateComponentBean> listPage() throws Exception {
        return BeanUtils.copyListProperties(
            templateComponentRepository.findByUnumIsvalidAndUnumUnivIdOrderByUnumCompDisplayOrderAsc(1, RequestUtility.getUniversityId()),
            TemplateComponentBean.class
        );
    }
}
