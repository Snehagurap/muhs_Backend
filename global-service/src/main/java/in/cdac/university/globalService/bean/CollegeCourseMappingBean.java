package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CollegeCourseMappingBean {

    private Long unumColCourseId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;

    @NotNull(message = "Faculty is mandatory")
    private Integer unumCfacultyId;

    @NotNull(message = "College is mandatory")
    private Long unumCollegeId;

    @NotNull(message = "Courses to map are mandatory")
    private List<Long> mappedCourses;

    private Long unumCourseId;
    private Integer unumCoursePresentAffStatusId;
    private Long unumEntryUid;
    private Integer unumUnivId;
    private String ustrCoursePresentAffiliationNo;
    private String ustrDescription;
}
