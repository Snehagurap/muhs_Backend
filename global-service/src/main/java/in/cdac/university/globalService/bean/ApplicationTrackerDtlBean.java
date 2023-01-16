package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    private Long unumApplicationStatusSno;

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

    private Date udtCoaDate;

    private Date udtDocDate;

    private Date udtEcDate;

    private Date udtFtaDate;

    private Date udtGrDate;

    private Date udtLoiDate;

    private Date udtLopDate;

    private Date udtNocDate;

    private Date udtOutDate;

    private Long unumApplicationDocId;

    private Integer unumApplicationLevelId;

    private Integer unumApprovalStatusid;

    private Integer unumDecisionStatusId;

    private Date unumLstModDate;

    private Long unumLstModUid;

    @Column(name="unum_out_no")
    private String unumOutNo;

    @Column(name="unum_stream_id")
    private Long unumStreamId;

    private String ustrApplicationDocNo;

    private String ustrCoaNo;

    private String ustrEcNo;

    private String ustrFtaNo;

    private String ustrGrNo;

    private String ustrLoiNo;

    private String ustrLopNo;

    private String ustrNocNo;

    private List<CheckListBean> checkList;
}
