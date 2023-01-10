package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.FacultyStreamDtlBean;
import in.cdac.university.globalService.bean.MappedComboBean;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.repository.FacultyStreamDtlRepository;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class FacultyStreamDtlService {

    @Autowired
    FacultyStreamDtlRepository facultyStreamDtlRepository;

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private Language language;

    public List<FacultyStreamDtlBean> getStreamComboByFaculty(Integer facultyId) throws Exception {
        return BeanUtils.copyListProperties(
                facultyStreamDtlRepository.findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(facultyId, 1, RequestUtility.getUniversityId()), FacultyStreamDtlBean.class
        );
    }

    public ServiceResponse getStreamsByFacultyId(Integer facultyId) throws Exception{
        List<GmstStreamMst> allStreams = streamRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());

        Set<Long> mappedStreamIds = facultyStreamDtlRepository.findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(facultyId,1,RequestUtility.getUniversityId())
                .stream()
                .map(GmstFacultyStreamDtl:: getUnumStreamId)
                .collect(toSet());

        List<ComboBean> mappedStreams = new ArrayList<>();
        List<ComboBean> notMappedStreams = new ArrayList<>();

        for(GmstStreamMst gmstStreamMst : allStreams) {
            ComboBean comboBean = new ComboBean(gmstStreamMst.getUnumStreamId().toString(),gmstStreamMst.getUstrStreamFname());
            if(mappedStreamIds.contains(gmstStreamMst.getUnumStreamId()))
                mappedStreams.add(comboBean);
            else
                notMappedStreams.add(comboBean);
        }
        return ServiceResponse.builder()
                .status(1)
                .responseObject(new MappedComboBean(mappedStreams, notMappedStreams))
                .build();

    }

    @Transactional
    public ServiceResponse saveMappingDetails(FacultyStreamDtlBean facultyStreamDtlBean) throws RuntimeException {
        // get existing mapped streams
        List<GmstFacultyStreamDtl> alreadyMappedStreams = facultyStreamDtlRepository.findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(
                facultyStreamDtlBean.getUnumCfacultyId(),facultyStreamDtlBean.getUnumIsvalid(),facultyStreamDtlBean.getUnumUnivId());

        Set<Long> mappedStreamsSet = new HashSet<>(facultyStreamDtlBean.getMappedStreamIds());

        // Streams to delete
        List<GmstFacultyStreamDtl> streamsToDelete = new ArrayList<>();

        for(GmstFacultyStreamDtl gmstFacultyStreamDtl : alreadyMappedStreams){

            if(mappedStreamsSet.contains(gmstFacultyStreamDtl.getUnumStreamId()))
                mappedStreamsSet.remove(gmstFacultyStreamDtl.getUnumStreamId());
            else
                streamsToDelete.add(gmstFacultyStreamDtl);
        }
        if(!streamsToDelete.isEmpty()) {
            List<Long> streamIdsToDelete = streamsToDelete.stream().map(GmstFacultyStreamDtl::getUnumStreamId).toList();
            facultyStreamDtlRepository.delete(facultyStreamDtlBean.getUnumCfacultyId(),facultyStreamDtlBean.getUnumUnivId(),streamIdsToDelete);
        }


        //Streams to add
        List<GmstFacultyStreamDtl> streamsToAdd = new ArrayList<>();
        for (Long streamId: mappedStreamsSet) {
            GmstFacultyStreamDtl gmstFacultyStreamDtl = BeanUtils.copyProperties(facultyStreamDtlBean, GmstFacultyStreamDtl.class);
            gmstFacultyStreamDtl.setUnumFacStreamId(facultyStreamDtlRepository.getNextId());
            gmstFacultyStreamDtl.setUnumStreamId(streamId);
           GmstStreamMst gmstStreamMst = streamRepository.findByUnumStreamIdAndUnumIsvalid(streamId,1);
            gmstFacultyStreamDtl.setUstrFacStreamFname(gmstStreamMst.getUstrStreamFname());

            streamsToAdd.add(gmstFacultyStreamDtl);
        }

        if (!streamsToAdd.isEmpty()){
            facultyStreamDtlRepository.saveAll(streamsToAdd);
        }


        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Faculty Stream Mapping"))
                .build();

    }

    public List<FacultyStreamDtlBean> getFacultyStreamList() throws Exception{
        List<GmstCoursefacultyMst> facultyList = facultyRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());
        List<GmstFacultyStreamDtl> gmstFacultyStreamDtls = facultyStreamDtlRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());

        List<FacultyStreamDtlBean> l3 = new ArrayList<>();

        Map<Integer, String> result =
                gmstFacultyStreamDtls.stream().collect(
                        Collectors.groupingBy(GmstFacultyStreamDtl::getUnumCfacultyId,
                                Collectors.mapping(GmstFacultyStreamDtl::getUstrFacStreamFname, Collectors.joining(","))
                        )
                );
        if(!result.isEmpty()){
          for(GmstCoursefacultyMst gmstCoursefacultyMst : facultyList ){
              String facultyWitnStream = result.get(gmstCoursefacultyMst.getUnumCfacultyId());
              if(facultyWitnStream != null){
                  FacultyStreamDtlBean facultyStreamDtlBean = new FacultyStreamDtlBean();
                    facultyStreamDtlBean.setUnumCfacultyId(gmstCoursefacultyMst.getUnumCfacultyId());
                    facultyStreamDtlBean.setUstrFacStreamFname(facultyWitnStream);
                    facultyStreamDtlBean.setGstrFacultyName(gmstCoursefacultyMst.getUstrCfacultyFname());
                  l3.add(facultyStreamDtlBean);
              }
          }
        }

        return l3;
    }
}
