package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicationDataBean {

    @ListColumn(name = "Application No.", order = 3)
    private Long unumApplicationId;

    private Long unumApplicantId;

    private Long unumNid;

    private Long unumNdtlId;

    private Integer unumIsvalid;

    private Date udtApplicationDate;

    private Date udtApplicationEntryDate;

    private Date udtApplicationSubmitDate;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Integer unumApplicationEntryStatus;

    private Long unumEntryUid;

    private Long unumMtemplateFor;

    private Long unumMtemplateType;

    private Long unumNDeptId;

    private Long unumNdtlCourseId;

    private Long unumNdtlDepartmentId;

    private Long unumNdtlFacultyId;

    private Integer unumUnivId;

    private String ustrApplicationDescription;

    private Integer unumCtypeId;

    private String ustrApplicantName;

    private Long unumMtempleId;

    @ListColumn(name = "Status", order = 4)
    private String statusName;

    @ListColumn(name = "Notification")
    private String notificationName;

    @ListColumn(name = "Faculty Name", order = 2)
    private String facultyName;

    private String applicantTypeName;

    private Date udtNDt;

    private String courseTypeName;

    private String applicantUserName;
}
