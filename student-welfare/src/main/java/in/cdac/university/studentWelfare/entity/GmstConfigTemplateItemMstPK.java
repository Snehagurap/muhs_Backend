package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GmstConfigTemplateItemMstPK implements Serializable {
	@Column(name="unum_temple_item_id")
	private Long unumTempleItemId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}