package in.cdac.university.planningBoard.bean;

import in.cdac.university.planningBoard.util.annotations.ComboKey;
import in.cdac.university.planningBoard.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NotificationDetailBean {

    @ComboKey
    private Long unumNdtlId;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Integer unumCoursetypeId;
    private Integer unumDepartmentId;
    private Long unumDocId;
    private Long unumEntryUid;
    private Integer unumFacultyId;
    private Integer unumNotificationTypeId;

    @ComboKey(index = 2)
    private Long unumNid;
    private Integer unumSNo;
    private Integer unumSnoDisplayorder;
    private Integer unumUnivId;
    private String ustrDescription;
    private String ustrFilePath;
    private String ustrNdtlSnoDescription;
    private Long unumMtempleId;

    @ComboValue
    private String notificationName;
}
