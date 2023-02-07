package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateHeaderMstPK implements Serializable {
	@Column(name="unum_temple_head_id")
	private Long unumTempleHeadId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}