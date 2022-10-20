package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateDtlPK implements Serializable {
	@Column(name="unum_templedtl_id")
	private Long unumTempledtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}