package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ConfigUiControlTypeBean;
import in.cdac.university.globalService.repository.ConfigUiControlTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigUiControlTypeService {

    @Autowired
    private ConfigUiControlTypeRepository configUiControlTypeRepository;

    public List<ConfigUiControlTypeBean> getAllControls(int universityId) {
        return BeanUtils.copyListProperties(
                configUiControlTypeRepository.findAllControls(universityId),
                ConfigUiControlTypeBean.class
        );
    }
}
