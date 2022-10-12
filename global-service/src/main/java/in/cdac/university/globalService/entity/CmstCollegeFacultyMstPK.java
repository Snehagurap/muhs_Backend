package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CmstCollegeFacultyMstPK implements Serializable {

	@Column(name="unum_col_fac_id")
	private Long unumColFacId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}