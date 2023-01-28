package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.DistrictBean;
import in.cdac.university.usm.entity.GbltDistrictMstImsc;
import in.cdac.university.usm.exception.ApplicationException;
import in.cdac.university.usm.repository.DistrictRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
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
                districtRepository.findAllByGnumIsvalidInAndGnumStatecodeOrderByStrDistNameAsc(List.of(1, 2), stateCode),
                DistrictBean.class);
    }

    public List<DistrictBean> getDistrictsByStateCodeAndStatus(Integer stateCode, Integer status) {
        return BeanUtils.copyListProperties(
                districtRepository.findAllByGnumIsvalidAndGnumStatecodeOrderByStrDistNameAsc(status, stateCode),
                DistrictBean.class);
    }

    public List<DistrictBean> getAllDistricts() {
        return BeanUtils.copyListProperties(
                districtRepository.findAll(),
                DistrictBean.class);
    }

    public List<DistrictBean> getDistricts(Integer stateCode) {
        return BeanUtils.copyListProperties(
                districtRepository.findByGnumStatecodeAndGnumIsvalidInOrderByStrDistStName(stateCode, List.of(1, 2)), DistrictBean.class
        );
    }

    public ServiceResponse getDistrictById(Integer distCode) {
        Optional<GbltDistrictMstImsc> districtMstImscOptional = districtRepository.findByGnumIsvalidInAndNumDistId(List.of(1, 2), distCode);

        if (districtMstImscOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("District", distCode));

        DistrictBean districtBean = BeanUtils.copyProperties(districtMstImscOptional.get(), DistrictBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(districtBean)
                .build();
    }


    @Transactional
    public ServiceResponse save(DistrictBean districtBean) {

        List<GbltDistrictMstImsc> districtMstImscList = districtRepository.findByGnumIsvalidInAndGnumStatecodeAndStrDistNameIgnoreCase(
                List.of(1, 2), districtBean.getGnumStatecode(), districtBean.getStrDistName()
        );
        if (!districtMstImscList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("District ", districtBean.getStrDistName()));
        }
        GbltDistrictMstImsc districtMstImsc = BeanUtils.copyProperties(districtBean, GbltDistrictMstImsc.class);
        Integer numDistId = districtRepository.getNextId();
        districtMstImsc.setNumDistId(numDistId);
        districtRepository.save(districtMstImsc);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("District"))
                .build();
    }

    @Transactional
    public ServiceResponse update(DistrictBean districtBean) {
        if (districtBean.getNumDistId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("District Id")));
        }

        // Duplicate Check
        List<GbltDistrictMstImsc> districtMstImscList = districtRepository.findByGnumIsvalidInAndGnumStatecodeAndStrDistNameIgnoreCaseAndNumDistIdNot(
                List.of(1, 2), districtBean.getGnumStatecode(), districtBean.getStrDistName(), districtBean.getNumDistId()
        );

        if (!districtMstImscList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("District ", districtBean.getStrDistName()));
        }

        // Create Log
        int noOfRecordsAffected = districtRepository.createLog(List.of((districtBean.getNumDistId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("District Id ", districtBean.getNumDistId()));
        }

        // Save new Data
        GbltDistrictMstImsc districtMstImsc = BeanUtils.copyProperties(districtBean, GbltDistrictMstImsc.class);
        districtRepository.save(districtMstImsc);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("District"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(Integer[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("District Id"));
        }

        List<GbltDistrictMstImsc> districtMstImscList = districtRepository.findByGnumIsvalidInAndNumDistIdIn(
                List.of(1, 2), List.of(idsToDelete)
        );

        if (districtMstImscList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("District ", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = districtRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("District"));
        }

        districtMstImscList.forEach(district -> {
            district.setGnumIsvalid(0);
            district.setGdtEntryDate(new Date());
        });

        districtRepository.saveAll(districtMstImscList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("District"))
                .build();
    }


}
