package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicationTrackerBean {

    private Long unumApplicationId;

    private Long unumApplicantId;

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
}
