package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.NotificationTypeBean;
import in.cdac.university.globalService.repository.NotificationTypeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    public List<NotificationTypeBean> getAllNotificationTypes() {
        return BeanUtils.copyListProperties(
                notificationTypeRepository.getAllNotificationTypes(),
                NotificationTypeBean.class
        );
    }
}
