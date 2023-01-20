package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GmstCoursePatternDtlPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "unum_course_pat_id")
    private Long unumCoursePatId;

    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;
}