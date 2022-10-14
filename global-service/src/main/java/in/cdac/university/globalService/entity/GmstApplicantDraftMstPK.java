package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstApplicantDraftMstPK implements Serializable {

	@Column(name="unum_applicant_draftid")
	private Long unumApplicantDraftid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}