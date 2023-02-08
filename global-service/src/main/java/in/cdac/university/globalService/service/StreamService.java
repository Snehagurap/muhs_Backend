package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.entity.GmstFacultyStreamDtl;
import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.repository.FacultyStreamDtlRepository;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.annotations.ComboValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StreamService {

    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private FacultyStreamDtlRepository facultyStreamDtlRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private Language language;

    public List<StreamBean> getAllStream(int status) {
    	
    	List<StreamBean> streamList = streamRepository.findAllByunumIsvalid(status).stream()
                .map(gmstStreamMst -> {
                    StreamBean streamBean = BeanUtils.copyProperties(gmstStreamMst, StreamBean.class);
                    streamBean.setUnumStreamId(gmstStreamMst.getUnumStreamId());
                    streamBean.setUstrStreamCode(gmstStreamMst.getUstrStreamCode());
                    streamBean.setUstrStreamFname(gmstStreamMst.getUstrStreamFname());
                    return streamBean;
                })
                .toList();
    	
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

    public List<StreamBean> getAllStreams() {
        return BeanUtils.copyListProperties(
                streamRepository.findAllByunumIsvalid(1), StreamBean.class
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

    public List<StreamBean> getStreamComboWithFacultyName() throws Exception {

        List<GmstFacultyStreamDtl> gmstFacultyStreamList = facultyStreamDtlRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId());

        List<GmstCoursefacultyMst> gmstfacultyMstList = facultyRepository.findByUnumIsvalid(1);
        Map<Integer, String> facultyMap = gmstfacultyMstList.stream().collect(
                Collectors.toMap(GmstCoursefacultyMst::getUnumCfacultyId,GmstCoursefacultyMst::getUstrCfacultyFname)
        );
        List<StreamBean> streamBeanList = gmstFacultyStreamList.stream().map(gmstFacultyStreamDtl -> {
//            Field field = null;
//            try {
//                field = StreamBean.class.getDeclaredField("ustrStreamFname");
//            } catch (NoSuchFieldException e) {
//                throw new RuntimeException(e);
//            }
//            if(field.isAnnotationPresent(ComboValue.class)){
//
//            field.setAccessible(true);
//                ComboValue comboValue = field.getAnnotation(ComboValue.class);
//                Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) field.get(StreamBean.class);
//                ComboValue targetvalue = new ComboValue(){
//
//                    @Override
//                    public Class<? extends Annotation> annotationType() {
//                        return null;
//                    }
//
//                    @Override
//                    public int order() {
//                        return 2;
//                    }
//
//                    @Override
//                    public String startSeparator() {
//                        return "/";
//                    }
//
//                    @Override
//                    public String endSeparator() {
//                        return null;
//                    }
//                };
//              //  annotations.put(ComboValue.class, targetvalue);
//                changeAnnotationValue(comboValue, "value", targetvalue);
//            }

            StreamBean streamBean = BeanUtils.copyProperties(gmstFacultyStreamDtl, StreamBean.class);
            streamBean.setUstrStreamFname(facultyMap.getOrDefault(gmstFacultyStreamDtl.getUnumCfacultyId(),"")+"/"+gmstFacultyStreamDtl.getUstrFacStreamFname());
            return streamBean;
        }).toList();
        return streamBeanList;
    }

    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key,newValue);
        return oldValue;
    }
}
