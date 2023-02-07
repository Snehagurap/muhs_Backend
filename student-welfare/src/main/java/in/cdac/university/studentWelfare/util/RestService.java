package in.cdac.university.studentWelfare.util;

import in.cdac.university.studentWelfare.bean.NotificationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestService {

    @Autowired
    private RestUtility restUtility;

    public NotificationBean getNotificationById(Long notificationId) {
        return restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + notificationId, NotificationBean.class);
    }
}
