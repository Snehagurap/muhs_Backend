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

	@ListColumn(order = 4, name = "Application No.")
    private Long unumApplicationId;

    private Long unumApplicantId;
    
    @ListColumn(order = 2, name = "Notification")
    private Long unumNid;

    private Long unumNdtlId;

    private Integer unumApplicationStatusSno;

    private Integer unumIsvalid;

    private Date udtApplicationStatusDt;

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
    
    @ListColumn(order = 3, name = "Faculty Name")
    private String facultyName;
	
    @ListColumn(order = 6, name = "Applicant Name")
	private String applicantName;
    
    @ListColumn(order = 5, name = "Applicant Date")
    private Date udtApplicationDate;
}
