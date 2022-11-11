package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateSubHeaderService {

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;

    public List<TemplateSubHeaderBean> listPageData() {
        return null;
    }
}
