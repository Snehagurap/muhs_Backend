package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstEmpMstPK implements Serializable {
	@Column(name="unum_emp_id")
	private Long unumEmpId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}