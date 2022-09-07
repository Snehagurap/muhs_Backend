package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.ModuleBean;
import in.cdac.university.usm.repository.ModuleRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public List<ModuleBean> getAllModules() {
        return BeanUtils.copyListProperties(moduleRepository.findAllByGblIsvalidOrderByGstrModuleName(1), ModuleBean.class);
    }
}
