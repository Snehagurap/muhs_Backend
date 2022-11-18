package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateComponentRepository;
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

    public List<TemplateComponentBean> listPage() {
        return new ArrayList<>();
    }
}
