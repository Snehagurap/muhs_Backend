package in.cdac.university.globalService.service;

import in.cdac.university.globalService.repository.TemplateHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateHeaderService {

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;
}
