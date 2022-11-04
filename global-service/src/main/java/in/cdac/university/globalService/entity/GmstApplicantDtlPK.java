package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstApplicantDtlPK implements Serializable {

	@Column(name="unum_applicant_doc_id")
	private Long unumApplicantDocId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}