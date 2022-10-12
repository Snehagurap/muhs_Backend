package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.NotificationReceiptBean;
import in.cdac.university.globalService.repository.NotificationReceiptRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationReceiptService {

    @Autowired
    private NotificationReceiptRepository notificationReceiptRepository;

    public List<NotificationReceiptBean> getAllNotificationReceipts() throws Exception {
        return BeanUtils.copyListProperties(
                notificationReceiptRepository.getAllNotificationReceipts(RequestUtility.getUniversityId()),
                NotificationReceiptBean.class
        );
    }
}
