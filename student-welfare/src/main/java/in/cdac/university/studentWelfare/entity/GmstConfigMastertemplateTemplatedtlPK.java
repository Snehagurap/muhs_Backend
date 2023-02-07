package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigMastertemplateTemplatedtlPK implements Serializable {

	@Column(name="unum_mtempledtl_id")
	private Long unumMtempledtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}