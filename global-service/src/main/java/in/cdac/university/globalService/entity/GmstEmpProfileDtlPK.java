package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstEmpProfileDtlPK implements Serializable {

	@Column(name="unum_profile_id")
	private Long unumProfileId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}