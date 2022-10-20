package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstApplicantTypeMstPK implements Serializable {

	@Column(name="unum_applicant_type_id")
	private Long unumApplicantTypeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}