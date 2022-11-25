package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NotificationDetailBean {

    private Long unumNdtlId;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Integer unumCoursetypeId;
    private Integer unumDepartmentId;
    private Long unumDocId;
    private Long unumEntryUid;
    private Integer unumFacultyId;
    private Integer unumNotificationTypeId;
    private Long unumNid;
    private Integer unumSNo;
    private Integer unumSnoDisplayorder;
    private Integer unumUnivId;
    private String ustrDescription;
    private String ustrFilePath;
    private String ustrNdtlSnoDescription;
    private Long unumMtempleId;
}
