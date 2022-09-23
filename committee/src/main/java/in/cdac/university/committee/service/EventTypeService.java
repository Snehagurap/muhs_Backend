package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.EventTypeBean;
import in.cdac.university.committee.repository.EventTypeRepository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventTypeBean> getEventTypes() throws Exception {
        Integer universityId = RequestUtility.getUniversityId();

        return BeanUtils.copyListProperties(
                eventTypeRepository.findByUnumIsvalidAndUnumUnivIdOrderByUstrEventTypenameAsc(1, universityId),
                EventTypeBean.class
        );
    }
}
