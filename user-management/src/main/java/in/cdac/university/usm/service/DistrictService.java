package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.DistrictBean;
import in.cdac.university.usm.entity.GbltDistrictMstImsc;
import in.cdac.university.usm.repository.DistrictRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private Language language;

    public List<DistrictBean> getAllDistricts(Integer stateCode) {
        return BeanUtils.copyListProperties(
                districtRepository.findAllByGnumIsvalidAndGnumStatecodeOrderByStrDistNameAsc(1, stateCode),
                DistrictBean.class);
    }

    public List<DistrictBean> getAllDistricts() {
        return BeanUtils.copyListProperties(
                districtRepository.findAll(),
                DistrictBean.class);
    }

    public List<DistrictBean> getDistricts(Integer stateCode) {
        return BeanUtils.copyListProperties(
                districtRepository.findByGnumStatecodeAndGnumIsvalidOrderByStrDistStName(stateCode,1),DistrictBean.class
        );
    }

    public ServiceResponse getDistrictById(Integer distCode) {
        Optional<GbltDistrictMstImsc> districtMstImscOptional = districtRepository.findByNumDistIdAndGnumIsvalidNot(distCode, 0);

        if (districtMstImscOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("District", distCode));

        DistrictBean districtBean = BeanUtils.copyProperties(districtMstImscOptional.get(), DistrictBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(districtBean)
                .build();
    }
}
