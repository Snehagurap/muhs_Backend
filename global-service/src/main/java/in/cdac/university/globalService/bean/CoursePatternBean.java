package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class CoursePatternBean {

    @ListColumn(omit = true)
    private Long unumCoursePatId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDate;

    @ListColumn(name = "Faculty", order = 4)
    private String facultyName;

    private Integer unumCfacultyId;

    @ListColumn(name = "Course", order = 3)
    private String courseName;

    private Long unumCourseId;

    @ListColumn(name = "CourseType", order = 2)
    private String courseTypeName;

    private Long unumCtypeId;

    private Integer unumDeptId;

    private Long unumEntryUid;

    private Long unumLstModUid;

    @ListColumn(name = "Stream", order = 5)
    private String streamName;

    private Long unumStreamId;

    private Integer unumUnivId;

    private String ustrCourseCode;

    private String ustrCoursePatCode;

    private String ustrCoursePatDocPath;

    @ListColumn(name = "Pattern", order = 6)
    private String ustrCoursePatFname;

    private String ustrCoursePatSname;

    private String ustrDescription;

    private String ustrReasonForNewPat;
}
