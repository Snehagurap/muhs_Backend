package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.EventBean;
import in.cdac.university.globalService.bean.TeacherBean;
import in.cdac.university.globalService.repository.TeacherRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Constants;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.RestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RestUtility restUtility;

    public List<TeacherBean> listPageData(int status) throws Exception {
        return BeanUtils.copyListProperties(
               teacherRepository.listPageData(status, RequestUtility.getUniversityId()),
               TeacherBean.class
        );
    }

    public List<TeacherBean> getCommitteeMembers(Long eventId) throws Exception {
        if (eventId == null)
            return new ArrayList<>();

        EventBean eventBean = restUtility.get(RestUtility.SERVICE_TYPE.COMMITTEE, Constants.URL_GET_EVENT + eventId, EventBean.class);
        if (eventBean == null) {
            return new ArrayList<>();
        }


        System.out.println(eventBean);

        return BeanUtils.copyListProperties(
                teacherRepository.listPageData(1, RequestUtility.getUniversityId()),
                TeacherBean.class
        );
    }
}
