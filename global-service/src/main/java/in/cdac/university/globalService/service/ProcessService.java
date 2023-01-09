package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ProcessBean;
import in.cdac.university.globalService.entity.GmstProcessMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ProcessRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private Language language;

    public List<ProcessBean> getAllProcess() throws Exception {
        return BeanUtils.copyListProperties(
                processRepository.getAllProcess(RequestUtility.getUniversityId()),
                ProcessBean.class);
    }

    public List<ProcessBean> listPageData(Integer status) throws Exception {
        return BeanUtils.copyListProperties(
                processRepository.listPageData(status),
                ProcessBean.class
        );
    }

    public ServiceResponse getProcess(Integer processId) {
        Optional<GmstProcessMst> processMstOptional = processRepository.findByUnumIsvalidInAndUnumProcessId(
                List.of(1, 2), processId
        );

        if (processMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Process", processId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(processMstOptional.get(), ProcessBean.class))
                .build();
    }


    @Transactional
    public ServiceResponse save(ProcessBean processBean) {
        //for duplicate
        List<GmstProcessMst> processList = processRepository.findByUnumIsvalidInAndUstrProcessNameIgnoreCase(
                List.of(1, 2), processBean.getUstrProcessName()
        );
        if (!processList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Process", processBean.getUstrProcessName()));
        }

        GmstProcessMst gmstProcessMst = BeanUtils.copyProperties(processBean, GmstProcessMst.class);
        gmstProcessMst.setUnumProcessId(processRepository.getNextId());
        processRepository.save(gmstProcessMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Process"))
                .build();
    }

    //for update
    @Transactional
    public ServiceResponse update(ProcessBean processBean) throws Exception {
        if (processBean.getUnumProcessId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Process Id")));
        }

        // Duplicate Check
        List<GmstProcessMst> processMsts = processRepository.findByUnumIsvalidInAndUstrProcessNameIgnoreCaseAndUnumProcessIdNot(
                List.of(1, 2), processBean.getUstrProcessName(), processBean.getUnumProcessId()
        );

        if (!processMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Process", processBean.getUstrProcessName()));
        }

        // Create Log
        int noOfRecordsAffected = processRepository.createLog(List.of((processBean.getUnumProcessId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Process Id", processBean.getUnumProcessId()));
        }

        // Save new Data
        GmstProcessMst processMst = BeanUtils.copyProperties(processBean, GmstProcessMst.class);
        processMst.setUnumLstModUid(RequestUtility.getUserId());
        processMst.setUdtLstModDt(new Date());
        processRepository.save(processMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Process"))
                .build();
    }

    //for delete
    @Transactional
    public ServiceResponse delete(ProcessBean processBean, Integer[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Process Id"));
        }

        List<GmstProcessMst> processMsts = processRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumProcessIdIn(
                List.of(1, 2), processBean.getUnumUnivId(), List.of(idsToDelete)
        );

        if (processMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Process Id", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = processRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Process"));
        }

        processMsts.forEach(post -> {
            post.setUnumIsvalid(0);
            post.setUdtEntryDate(processBean.getUdtEntryDate());
        });

        processRepository.saveAll(processMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Process"))
                .build();
    }
}
