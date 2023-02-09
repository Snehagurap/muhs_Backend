package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

import in.cdac.university.globalService.util.annotations.ListColumn;


@Getter
@Setter
@ToString
public class ApplicationTrackerBean {

	@ListColumn(order = 4, name = "Application No.", width = "15%")
    private Long unumApplicationId;

    private Long unumApplicantId;
    
    @ListColumn(order = 2, name = "Notification No.", omit = true)
    private Long unumNid;

    private Long unumNdtlId;

    private Integer unumApplicationStatusSno;

    private Integer unumIsvalid;

    private Date udtApplicationStatusDt;

    @ListColumn(omit = true)
    private Integer unumApplicationLevelId;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;


    private Integer unumApplicationStatusId;

    private Long unumEntryUid;

    private Long unumMtempleId;

    private Long unumNdtlCourseId;

    private Long unumNdtlDepartmentId;

    private Long unumNdtlFacultyId;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrStatusBy;

    private Long unumCtypeId;
    
    @ListColumn(order = 3, name = "Faculty Name", width = "15%")
    private String facultyName;
	
    @ListColumn(order = 6, name = "Applicant Name", width = "20%")
	private String applicantName;
    
    @ListColumn(order = 5, name = "Application Date", width = "15%")
    private Date udtApplicationDate;

    private Date udtCentreGovtApprovalDate;

    private Date udtCentreOutdate;

    private Date udtCoaDate;

    private Date udtFtaDate;

    private Date udtStateGovtApprovalDate;

    private String udtStateOutdate;

    private Integer unumApprovalStatusid;

    private Integer unumCentreGovtApprovalTypeId;

    private Integer unumCoaStatusId;

    private Integer unumDecisionStatusId;

    private Date unumLstModDate;

    private Long unumLstModUid;

    private Long unumStateGovtApprovalTypeId;

    private Long unumStreamId;

    private String ustrCentreGovtApprovalDocPath;

    private String ustrCentreOutno;

    private String ustrCoaNo;

    private String ustrFtaNo;

    private String ustrStateGovtApprovalDocPath;

    private String ustrStateOutno;

    @ListColumn(order = 7, name = "Status", width = "20%")
    private String applicationStatus;
}
