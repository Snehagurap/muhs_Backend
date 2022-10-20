package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.NotificationTypeBean;
import in.cdac.university.globalService.entity.GmstNotificationTypeMst;
import in.cdac.university.globalService.entity.GmstNotificationTypeMstPK;
import in.cdac.university.globalService.repository.NotificationTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private Language language;

    public List<NotificationTypeBean> getAllValidNotificationTypes() throws Exception {
        return BeanUtils.copyListProperties(
                notificationTypeRepository.getAllValidNotificationTypes(RequestUtility.getUniversityId()),
                NotificationTypeBean.class
        );
    }

    public List<NotificationTypeBean> getAllNotificationTypes() throws Exception {
        return BeanUtils.copyListProperties(
                notificationTypeRepository.findByUnumIsvalidInOrderByUstrNtypeFname(List.of(0, 1)),
                NotificationTypeBean.class
        );
    }

    public ServiceResponse getNotificationTypeById(Integer notificationTypeId) {
        Optional<GmstNotificationTypeMst> notificationTypeMstOptional = notificationTypeRepository.findById(new GmstNotificationTypeMstPK(notificationTypeId, 1));
        if (notificationTypeMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Type", notificationTypeId));

        return ServiceResponse.successObject(
                BeanUtils.copyProperties(notificationTypeMstOptional.get(), NotificationTypeBean.class)
        );
    }
}
