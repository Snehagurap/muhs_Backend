package in.cdac.university.globalService.service;


import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamService {

    @Autowired
    StreamRepository streamRepository;


    public List<StreamBean> getStreamCombo() throws Exception {
        return BeanUtils.copyListProperties(
                streamRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId()), StreamBean.class
        );
    }
}
