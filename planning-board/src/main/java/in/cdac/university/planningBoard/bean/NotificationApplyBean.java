package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class NotificationApplyBean {

    private String notificationName;
    private String notificationYear;
    private String notificationDate;
    private Long notificationId;

    List<NotificationApplyDetailBean> notificationDetails;
    List<NotificationDocumentBean> notificationDocuments;
}
