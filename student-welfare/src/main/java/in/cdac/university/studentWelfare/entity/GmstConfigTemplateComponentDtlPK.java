package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

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