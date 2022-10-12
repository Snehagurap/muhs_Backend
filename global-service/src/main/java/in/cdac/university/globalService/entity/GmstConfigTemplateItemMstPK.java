package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GmstConfigTemplateItemMstPK implements Serializable {
	@Column(name="unum_templ_item_id")
	private Long unumTemplItemId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}