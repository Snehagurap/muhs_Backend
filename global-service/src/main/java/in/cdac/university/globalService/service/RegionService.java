package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.RegionBean;
import in.cdac.university.globalService.repository.RegionRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionBean> regionList() throws Exception {
        return BeanUtils.copyListProperties(
                regionRepository.getAllRegions(RequestUtility.getUniversityId()),
                RegionBean.class
        );
    }
}
