package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.NotificationStyleBean;
import in.cdac.university.globalService.repository.NotificationStyleRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationStyleService {

    @Autowired
    private NotificationStyleRepository notificationStyleRepository;

    public List<NotificationStyleBean> getAllNotificationStyles() {
        return BeanUtils.copyListProperties(
                notificationStyleRepository.getAllNotificationStyle(),
                NotificationStyleBean.class
        );
    }
}
