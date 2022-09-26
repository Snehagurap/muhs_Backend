package in.cdac.university.globalService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GmstCourseMstPK implements Serializable {
	@Column(name="unum_course_id")
	private Long unumCourseId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}