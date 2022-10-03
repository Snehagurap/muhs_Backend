package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstTeacherMstPK implements Serializable {
	@Column(name="unum_teacher_id")
	private Long unumTeacherId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}