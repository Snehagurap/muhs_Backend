package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateSubheaderMstPK implements Serializable {

	@Column(name="unum_temple_subhead_id")
	private Long unumTempleSubheadId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}