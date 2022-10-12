package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstEmpCurDtlPK implements Serializable {

	@Column(name="unum_emp_cur_id")
	private Long unumEmpCurId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}