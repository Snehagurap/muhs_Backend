package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.repository.TemplateItemRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TemplateItemService {

    @Autowired
    private TemplateItemRepository templateItemRepository;

    public ServiceResponse save(TemplateItemBean templateItemBean) {
        return null;
    }

    public List<TemplateItemBean> getAllItems(Integer universityId) {
        return BeanUtils.copyListProperties(
                templateItemRepository.getAllItems(universityId),
                TemplateItemBean.class
        );
    }
}
