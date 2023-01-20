package in.cdac.university.planningBoard.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationDataBean {


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


    private String statusName;

    private String notificationName;

    private String facultyName;

    private String applicantTypeName;

    private Date udtNDt;

    private String courseTypeName;

    private String applicantUserName;
}
