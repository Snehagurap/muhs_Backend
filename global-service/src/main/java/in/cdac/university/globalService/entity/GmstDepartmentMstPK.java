package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GmstDepartmentMstPK implements Serializable {

	@Column(name="unum_dept_id")
	private Integer unumDeptId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}