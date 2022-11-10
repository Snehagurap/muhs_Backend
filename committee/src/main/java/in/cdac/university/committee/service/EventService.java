package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.EventBean;
import in.cdac.university.committee.bean.CollegeBean;
import in.cdac.university.committee.entity.GbltEventMst;
import in.cdac.university.committee.repository.EventRepository;
import in.cdac.university.committee.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private Language language;

    public ServiceResponse save(EventBean eventBean) {
        // Check for duplicate event name
        Optional<GbltEventMst> eventMstOptional = eventRepository.duplicateCheck(eventBean.getUnumUnivId(),
                eventBean.getUstrEventName(), eventBean.getUdtEventFromdt(), eventBean.getUdtEventTodt());

        if (eventMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Event", eventBean.getUstrEventName()));
        }

        // Find College
        String url = RestUtility.SERVICE_TYPE.GLOBAL +
        Constants.URL_GET_COLLEGE + eventBean.getUnumCollegeId();
        CollegeBean collegeBean = restUtility.get(
                RestUtility.SERVICE_TYPE.GLOBAL,
                Constants.URL_GET_COLLEGE + eventBean.getUnumCollegeId(),
                CollegeBean.class);

        if (collegeBean == null) {
            return ServiceResponse.errorResponse(language.notFoundForId("College", eventBean.getUnumCollegeId()));
        }

        GbltEventMst eventMst = BeanUtils.copyProperties(eventBean, GbltEventMst.class);
        Long eventId = eventRepository.getNextId();
        eventMst.setUnumEventid(eventId);
        eventMst.setUstrCollegeName(collegeBean.getUstrColFname());
        eventRepository.save(eventMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Event"))
                .build();
    }

    public List<EventBean> getEventCombo() {
        return BeanUtils.copyListProperties(
                eventRepository.activeEventList(RequestUtility.getUniversityId()),
                EventBean.class
        );
    }

    public List<EventBean> getEventCombo(Long committeeId) {
        return eventRepository.activeEventListByCommitteeId(RequestUtility.getUniversityId(), committeeId)
                .stream()
                .map(event -> {
                    EventBean eventBean = BeanUtils.copyProperties(event, EventBean.class);
                    eventBean.setIsEventStarted(event.getUdtEventFromdt().before(new Date()) ? 1 : 0);
                    return eventBean;
                })
                .toList();
    }

    public List<EventBean> getListPageData(Long committeeId) {
        return BeanUtils.copyListProperties(
                eventRepository.listPageData(RequestUtility.getUniversityId(), committeeId),
                EventBean.class
        );
    }

    public ServiceResponse getEvent(Long eventId) {
        if (eventId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Event Id"));
        }

        Optional<GbltEventMst> eventMst = eventRepository.findByUnumEventidAndUnumUnivIdAndUnumIsvalid(eventId, RequestUtility.getUniversityId(), 1);

        if (eventMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Event", eventId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(eventMst.get(), EventBean.class))
                .build();
    }
}
