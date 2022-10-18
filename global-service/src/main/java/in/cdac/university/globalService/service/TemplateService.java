package in.cdac.university.globalService.service;

import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public ServiceResponse getTemplate(Long templateId) {
        return null;
    }
}
