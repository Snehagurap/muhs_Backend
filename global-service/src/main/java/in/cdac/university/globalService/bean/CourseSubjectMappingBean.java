package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CourseSubjectMappingBean {

    @ComboValue
    private Long unumCourseSubId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumUnivId;

    @NotNull(message = "CourseId is mandatory")
    private Long unumCourseId;

    private Long unumSubId;

    private Long ustrDescription;

    @NotNull(message = "Subjects to map are mandatory")
    private List<Long> mappedSubjects;
}
