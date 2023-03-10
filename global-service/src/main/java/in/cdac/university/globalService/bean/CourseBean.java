package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CourseBean {

    @ComboKey
    @ListColumn(omit = true)
    private Long unumCourseId;

    @NotNull(message = "Status is mandatory")
    private Integer unumIsvalid;

    @NotNull(message = "Effective From date is mandatory")
    private Date udtEffFrom;

    private Date udtEffTo;
    private Date udtEntryDate;

    @NotNull(message = "Course Duration is mandatory")
    private Integer unumCdCategoryId;


    private Integer unumCfacultyId;
    private Integer unumCourseAdmissionmode;

    @Range(min = 0, max = 99, message = "Course Duration in months must be less than or equal to 99")
    @NotNull(message = "Course Duration in Months is mandatory")
    private Integer unumCourseDurationInmm;

    @Range(min = 0, max = 99, message = "Course Duration in years must be less than or equal to 99")
    @NotNull(message = "Course Duration in Years is mandatory")
    private Integer unumCourseDurationInyy;

    private String unumCourseLangMedium;

    @Range(min = 0, max = 99, message = "Maximum months must be less than or equal to 99")
    private Integer unumCourseMaxmonths;

    @Range(min = 0, max = 99, message = "Maximum Years must be less than or equal to 99")
    private Integer unumCourseMaxyears;

    @Range(min = 0, max = 99, message = "Minimum months must be less than or equal to 99")
    private Integer unumCourseMinmonths;

    @Range(min = 0, max = 99, message = "Minimum Years must be less than or equal to 99")
    private Integer unumCourseMinyears;

    private Integer unumCoursePattern;


    @NotNull(message = "Course Type is mandatory")
    private Integer unumCtypeId;
    private Long unumEntryUid;
    private Integer unumExonnCfcoid;
    private Integer unumExonnLevelid;
    private Integer unumIntranetcourseid;

    @Range(min = 0, max = 1, message = "Is Academic Department can be 0 or 1")
    private Integer unumIsacaddept = 0;

    @Range(min = 0, max = 1, message = "Is Carry Forward can be 0 or 1")
    private Integer unumIscarryforward = 0;

    @Range(min = 0, max = 1, message = "Is Eligibility Department can be 0 or 1")
    private Integer unumIselegdept = 0;

    @Range(min = 0, max = 1, message = "Is Examination Department can be 0 or 1")
    private Integer unumIsexamdept = 0;

    @Range(min = 0, max = 1, message = "Is Scale Down can be 0 or 1")
    private Integer unumIsscaledown = 0;
    private Integer unumUnivId;

    @ListColumn(order = 3, name = "Course Code", width = "5%")
    private String ustrCourseCode;

    @ComboValue
    @NotNull(message = "Course Full name is mandatory")
    @ListColumn(order = 2, name = "Course Name", width = "35%")
    private String ustrCourseFname;

    private String ustrCourseSname;
    private String ustrDescription;
    private String ustrExonnCocate;
    private String ustrExonnCoursecd;
    private String ustrExonnFacultycode;
    private String ustrTeachersCourseCode;

    private String ustrStuMinreqCtypeids;
    private String ustrStuMinreqCourseids;

    @ListColumn(order=6,name = "Institute Type", width = "20%")
    private Integer unumEligibleInstituteType;

    @ListColumn(order=5,name = "Faculty Type", width = "20%")
    private String ustrCfacultyName;

    @ListColumn(order=4,name = "Course Type", width = "20%")
    private String ustrCtypeName;
}
