package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMst;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateSubHeaderService {

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;

    public List<TemplateSubHeaderBean> listPageData(Long headerId) throws Exception {
        List<GmstConfigTemplateSubheaderMst> subHeadList = templateSubHeaderRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTemplHeadIdOrderByUnumSubheadDisplayOrderAsc(
                1, RequestUtility.getUniversityId(), headerId
        );
        return BeanUtils.copyListProperties(subHeadList, TemplateSubHeaderBean.class);
    }
}
