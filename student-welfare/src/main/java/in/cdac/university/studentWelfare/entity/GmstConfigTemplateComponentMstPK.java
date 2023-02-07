package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateComponentMstPK implements Serializable {
	@Column(name="unum_temple_comp_id")
	private Long unumTempleCompId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}