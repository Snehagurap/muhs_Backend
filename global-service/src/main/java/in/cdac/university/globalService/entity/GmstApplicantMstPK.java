package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

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