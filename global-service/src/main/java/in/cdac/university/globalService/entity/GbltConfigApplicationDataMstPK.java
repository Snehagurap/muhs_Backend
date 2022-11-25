package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GbltConfigApplicationDataMstPK implements Serializable {
	@Column(name="unum_application_id")
	private Long unumApplicationId;

	@Column(name="unum_applicant_id")
	private Long unumApplicantId;

	@Column(name="unum_nid")
	private Long unumNid;

	@Column(name="unum_ndtl_id")
	private Long unumNdtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}