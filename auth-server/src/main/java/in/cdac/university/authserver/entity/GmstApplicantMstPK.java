package in.cdac.university.authserver.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstApplicantMstPK implements Serializable {

	@Column(name="unum_applicant_id")
	private Long unumApplicantId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}