package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstCoursefacultyMstPK implements Serializable {

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}