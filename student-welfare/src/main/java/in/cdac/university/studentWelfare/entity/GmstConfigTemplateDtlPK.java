package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

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