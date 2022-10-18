package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateComponentDtlPK implements Serializable {

	@Column(name="unum_templ_comp_item_id")
	private Long unumTemplCompItemId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}