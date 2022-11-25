package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationApplyDetailBean {

    private String courseType;
    private String notificationType;
    private String faculty;

    private Long unumMtempleId;
    private Long unumNdtlId;
    private Long unumNid;
}
