package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class StreamService {

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private Language language;

    public List<StreamBean> getAllStream(int status) {

        return BeanUtils.copyListProperties(
                streamRepository.findAllByunumIsvalid(status),
                StreamBean.class
        );
    }

    public List<StreamBean> getStreamCombo() throws Exception {
        return BeanUtils.copyListProperties(
                streamRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId()), StreamBean.class
        );
    }

    @Transactional
    public ServiceResponse save(StreamBean streamBean) {
        // Duplicate check
        List<GmstStreamMst> streamMsts = streamRepository.findByUnumIsvalidInAndUstrStreamFnameIgnoreCase(
                List.of(1, 2), streamBean.getUstrStreamFname());

        if (!streamMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Stream", streamBean.getUstrStreamFname()));
        }
        GmstStreamMst gmstStreamMst = BeanUtils.copyProperties(streamBean, GmstStreamMst.class);

        // Generate New Id
        Long streamId = streamRepository.getNextId();
        gmstStreamMst.setUnumStreamId(streamId);
        streamRepository.save(gmstStreamMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Stream"))
                .build();
    }

    @Transactional
    public ServiceResponse updateStreamDetails(StreamBean streamBean) {
        if (streamBean.getUnumStreamId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Stream Id"));
        }

        // Duplicate check
        List<GmstStreamMst> subjectList = streamRepository.findByUnumIsvalidInAndUstrStreamFnameIgnoreCaseAndUnumStreamIdNot(
                List.of(1, 2), streamBean.getUstrStreamFname(), streamBean.getUnumStreamId());

        if (!subjectList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Stream", streamBean.getUstrStreamFname()));
        }

        // Create Log
        int noOfRecordsAffected = streamRepository.createLog(List.of(streamBean.getUnumStreamId()));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Stream", streamBean.getUnumStreamId()));
        }

        // Save new Data
        GmstStreamMst streamMst = BeanUtils.copyProperties(streamBean, GmstStreamMst.class);
        streamRepository.save(streamMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Stream"))
                .build();
    }

    @Transactional
    public ServiceResponse deleteStreamDetails(StreamBean streamBean, Long[] idsToDelete) {
        if (idsToDelete == null) {
            return ServiceResponse.errorResponse(language.mandatory("Stream Id"));
        }

        List<GmstStreamMst> streamMsts = streamRepository.findByUnumIsvalidInAndUnumStreamIdIn(
                List.of(1, 2), List.of(idsToDelete)
        );

        if (streamMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Stream Id", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRecordsAffected = streamRepository.createLog(List.of(idsToDelete));
        if (noOfRecordsAffected != idsToDelete.length) {
            throw new ApplicationException(language.notFoundForId("Stream", Arrays.toString(idsToDelete)));
        }

        // Save new Data
        streamMsts.forEach(stream -> {
            stream.setUnumIsvalid(0);
            stream.setUdtEntryDate(streamBean.getUdtEntryDate());
        });
        streamRepository.saveAll(streamMsts);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Stream"))
                .build();
    }

    public StreamBean getStreamById(Long streamId) {
        List<GmstStreamMst> streamById = streamRepository.findByUnumIsvalidInAndUnumStreamIdIn(
                List.of(1, 2), List.of(streamId)
        );
        if (streamById.isEmpty())
            throw new ApplicationException("Stream not found");
        return BeanUtils.copyProperties(
                streamById.get(0),
                StreamBean.class
        );
    }
}
