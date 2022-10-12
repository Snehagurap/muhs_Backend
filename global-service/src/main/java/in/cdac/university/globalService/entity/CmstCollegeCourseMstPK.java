package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CmstCollegeCourseMstPK implements Serializable {
	@Column(name="unum_col_course_id")
	private Long unumColCourseId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}