package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateComponentMstPK implements Serializable {
	@Column(name="unum_templ_comp_id")
	private Long unumTemplCompId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}