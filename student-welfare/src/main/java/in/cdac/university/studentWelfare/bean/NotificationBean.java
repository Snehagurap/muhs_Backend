package in.cdac.university.studentWelfare.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class NotificationBean {
    private Long unumNid;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Date udtNDt;
    private Date udtNOutDt;
    private Date udtValidFrm;
    private Date udtValidTo;
    private Long unumApprovingUserid;
    private Integer unumCfacultyId;

    private Long unumStreamId;
    
    private Integer unumDeptId;
    private Long unumEntryUid;
    private Integer unumIsAmmendment = 0;
    private Integer unumMainLangId;
    private Long unumMainNid;
    private Integer unumNRecepient;
    private Integer unumNStyle;
    private Integer unumNtypeId;
    private Integer unumUnivId;
    private String ustrAcademicYear;
    private String ustrApprovingOrderno;
    private String ustrApprovingUsername;
    private String ustrDescription;
    private String ustrNMainHeading;
    private String ustrNNo;
    private String ustrNOutNo;
    private String ustrNSubHeading;
    private List<NotificationDetailBean> notificationDetails;
    private String notificationTypeName;
}
