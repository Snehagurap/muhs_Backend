package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateHeaderMstPK implements Serializable {
	@Column(name="unum_templ_head_id")
	private Long unumTemplHeadId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}