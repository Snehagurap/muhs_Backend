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

	@Column(name="unum_temple_comp_item_id")
	private Long unumTempleCompItemId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}