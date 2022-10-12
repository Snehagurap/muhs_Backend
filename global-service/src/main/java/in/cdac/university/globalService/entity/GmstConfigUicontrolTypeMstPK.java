package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigUicontrolTypeMstPK implements Serializable {
	@Column(name="unum_uict_id")
	private Long unumUictId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}