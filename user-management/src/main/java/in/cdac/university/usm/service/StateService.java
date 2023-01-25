package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.StateBean;
import in.cdac.university.usm.entity.GbltStateMstImsc;
import in.cdac.university.usm.exception.ApplicationException;
import in.cdac.university.usm.repository.StateRepository;
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
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private Language language;

    public List<StateBean> getAllStates(Integer countryCode) {
        return BeanUtils.copyListProperties(stateRepository.findAllByGnumCountrycodeOrderByGstrStatenameAsc(countryCode), StateBean.class);
    }

    public List<StateBean> getStateByStateCode() {
        return BeanUtils.copyListProperties(stateRepository.findByGnumStatecodeAndGnumIsvalidOrderByGstrStatename(), StateBean.class);
    }

    public ServiceResponse getStateById(Integer stateCode) {
        Optional<GbltStateMstImsc> stateMstOptional = stateRepository.findByGnumIsvalidInAndGnumStatecode(
                List.of(1, 2), stateCode);

        if (stateMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("State ", stateCode));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(stateMstOptional.get(), StateBean.class))
                .build();
    }

    @Transactional
    public ServiceResponse save(StateBean stateBean) {
        //for duplicate check
        List<GbltStateMstImsc> stateMstImscList = stateRepository.findByGnumIsvalidInAndGstrStatenameIgnoreCase(
                List.of(1, 2), stateBean.getGstrStatename());
        if (!stateMstImscList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("State ", stateBean.getGstrStatename()));
        }
        GbltStateMstImsc stateMstImsc = BeanUtils.copyProperties(stateBean, GbltStateMstImsc.class);
        Integer stateCode = stateRepository.getNextId();
        stateMstImsc.setGnumStatecode(stateCode);
        stateRepository.save(stateMstImsc);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("State "))
                .build();
    }


    @Transactional
    public ServiceResponse update(StateBean stateBean) {
        if (stateBean.getGnumStatecode() == null) {
            return ServiceResponse.errorResponse((language.mandatory("State Id")));
        }

        // Duplicate Check
        List<GbltStateMstImsc> stateMstImscList = stateRepository.findByGnumIsvalidInAndGstrStatenameIgnoreCaseAndGnumStatecodeNot(
                List.of(1, 2), stateBean.getGstrStatename(), stateBean.getGnumStatecode()
        );

        if (!stateMstImscList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("State ", stateBean.getGstrStatename()));
        }

        // Create Log
        int noOfRecordsAffected = stateRepository.createLog(List.of((stateBean.getGnumStatecode())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("State Id ", stateBean.getGnumStatecode()));
        }

        // Save new Data
        GbltStateMstImsc stateMstImsc = BeanUtils.copyProperties(stateBean, GbltStateMstImsc.class);
        stateRepository.save(stateMstImsc);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("State"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(Integer[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("State Id"));
        }

        List<GbltStateMstImsc> stateMstImscList = stateRepository.findByGnumIsvalidInAndGnumStatecodeIn(
                List.of(1, 2), List.of(idsToDelete)
        );

        if (stateMstImscList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("State ", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = stateRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("State "));
        }

        stateMstImscList.forEach(state -> {
            state.setGnumIsvalid(0);
            state.setGdtEntrydate(new Date());
        });

        stateRepository.saveAll(stateMstImscList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("State "))
                .build();
    }

    public List<StateBean> getListPage(Integer countryCode, Integer status) {
        return BeanUtils.copyListProperties(
                stateRepository.listPageData(countryCode, status),
                StateBean.class
        );

    }
}
