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
	@Column(name="unum_temple_head_id")
	private Long unumTempleHeadId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}