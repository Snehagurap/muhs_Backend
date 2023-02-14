package in.cdac.university.globalService.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;


@Getter
@Setter
@ToString

public class EmployeeProfileBean {
    private Long unumProfileId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumCollegeId;
    private Long unumEntryUid;
    private Integer unumFacultyId;
    private Long unumCourseId;
    private Long unumSubId;
    private Long unumEmpId;
    private Integer unumUnivId;
    private String ustrDescription;

    private Long unumStreamId;

    private Integer unumCourseTypeId;

}
