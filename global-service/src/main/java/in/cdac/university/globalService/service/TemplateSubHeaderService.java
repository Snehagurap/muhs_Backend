package in.cdac.university.globalService.service;

import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateSubHeaderService {

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;
}
