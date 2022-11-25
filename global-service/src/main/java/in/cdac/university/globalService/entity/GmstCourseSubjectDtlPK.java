package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GmstCourseSubjectDtlPK implements Serializable {
    @Column(name="unum_course_sub_id")
    private Long unumCourseSubId;

    @Column(name="unum_isvalid")
    private Integer unumIsvalid;

}