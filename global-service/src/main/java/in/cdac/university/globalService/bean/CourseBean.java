package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseBean {

    @ListColumn(omit = true)
    private Long unumCourseId;

    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumCdCategoryId;
    private Integer unumCfacultyId;
    private Integer unumCourseAdmissionmode;
    private Integer unumCourseDurationInmm;
    private Integer unumCourseDurationInyy;
    private Integer unumCourseLangMedium;
    private Integer unumCourseMaxmonths;
    private Integer unumCourseMaxyears;
    private Integer unumCourseMinmonths;
    private Integer unumCourseMinyears;
    private Integer unumCoursePattern;
    private Integer unumCtypeId;
    private Long unumEntryUid;
    private Integer unumExonnCfcoid;
    private Integer unumExonnLevelid;
    private Integer unumIntranetcourseid;
    private Integer unumIsacaddept;
    private Integer unumIscarryforward;
    private Integer unumIselegdept;
    private Integer unumIsexamdept;
    private Integer unumIsscaledown;
    private Integer unumUnivId;

    @ListColumn(order = 3, name = "Course Code")
    private String ustrCourseCode;

    @ListColumn(order = 2, name = "Course Name")
    private String ustrCourseFname;

    private String ustrCourseSname;
    private String ustrDescription;
    private String ustrExonnCocate;
    private String ustrExonnCoursecd;
    private String ustrExonnFacultycode;
    private String ustrTeachersCourseCode;
}
