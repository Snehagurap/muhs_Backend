package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstCourseMstPK implements Serializable {
	@Column(name="unum_course_id")
	private Long unumCourseId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}