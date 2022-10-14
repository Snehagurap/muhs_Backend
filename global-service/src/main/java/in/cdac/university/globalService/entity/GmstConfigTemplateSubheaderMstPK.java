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

	@Column(name="unum_templ_subhead_id")
	private Long unumTemplSubheadId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}