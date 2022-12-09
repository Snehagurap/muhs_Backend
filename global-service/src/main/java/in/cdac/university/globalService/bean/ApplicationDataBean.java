package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@ToString
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
}
