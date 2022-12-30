package in.cdac.university.globalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstSubjectMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;

@Service
public class StreamService {
	
	@Autowired
	private StreamRepository streamRepository;

	@Autowired
    private Language language;
	
	public List<StreamBean> getAllStream(int status) throws Exception {
     
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
                List.of(1, 2),streamBean.getUstrStreamFname());
        
        if (!streamMsts.isEmpty()) {
        	return ServiceResponse.errorResponse(language.duplicate("Stream", streamBean.getUstrStreamCode()));
        }
        GmstStreamMst gmstStreamMst = BeanUtils.copyProperties(streamBean, GmstStreamMst.class);
        
        // Generate New Id
        Integer subjectId = streamRepository.getNextId();
        gmstStreamMst.setUnumStreamId(new Long(subjectId));
        System.out.print(gmstStreamMst);
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
        List.of(1, 2),streamBean.getUstrStreamFname(),streamBean.getUnumStreamId());
        
        if (!subjectList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Stream", streamBean.getUstrStreamCode()));
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
    public ServiceResponse deleteStreamDetails(StreamBean streamBean) {
        if (streamBean.getUnumStreamId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Stream Id"));
        }
       
        // Duplicate check
        List<GmstStreamMst> subjectList = streamRepository.findByUnumIsvalidInAndUstrStreamFnameIgnoreCaseAndUnumStreamIdNot(
        List.of(1, 2),streamBean.getUstrStreamFname(),streamBean.getUnumStreamId());

        if (!subjectList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Stream", streamBean.getUstrStreamCode()));
        }

        // Create Log
        int noOfRecordsAffected = streamRepository.createLog(List.of(streamBean.getUnumStreamId()));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Stream", streamBean.getUnumStreamId()));
        }
        
        // Save new Data
        GmstStreamMst streamMst = BeanUtils.copyProperties(streamBean, GmstStreamMst.class);
        streamMst.setUnumIsvalid(0);
        streamRepository.save(streamMst);
        
        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Stream"))
                .build();
 }
}
