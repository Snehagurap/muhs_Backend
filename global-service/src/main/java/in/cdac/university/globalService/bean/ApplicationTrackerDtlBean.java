package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicationTrackerDtlBean {

    @NotNull(message = "Application Id is mandatory")
    private Long unumApplicationId;

    @NotNull(message = "Applicant Id is mandatory")
    private Long unumApplicantId;

    @NotNull(message = "Notification Id is mandatory")
    private Long unumNid;

    @NotNull(message = "Notification Detail Id is mandatory")
    private Long unumNdtlId;

    private Integer unumApplicationStatusSno;

    private Integer unumIsvalid;

    private Date udtApplicationStatusDt;

    @NotNull(message = "Application Status Id is mandatory")
    private Integer unumApplicationStatusId;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    @NotNull(message = "Master Template Id is mandatory")
    private Long unumMtempleId;

    private Long unumNdtlCourseId;

    private Long unumNdtlDepartmentId;

    private Long unumNdtlFacultyId;

    private Long unumRecommendedforIntake;

    private Integer unumUnivId;

    private String ustrDescription;

    private Long unumDocSno;

    private String ustrDocName;

    @NotBlank(message = "Document Path is mandatory")
    private String ustrDocPath;

    private String ustrRemarks;

    private String ustrStatusBy;

    private Long unumCtypeId;
	
    private Date udtApplicationDate;
}
