package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

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