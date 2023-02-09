package in.cdac.university.studentWelfare.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltConfigApplicationDataDtlPK implements Serializable {
	@Column(name="unum_application_id")
	private Long unumApplicationId;

	@Column(name="unum_applicationdtl_id")
	private Long unumApplicationdtlId;

	@Column(name="unum_applicant_id")
	private Long unumApplicantId;

	@Column(name="unum_nid")
	private Long unumNid;

	@Column(name="unum_ndtl_id")
	private Long unumNdtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}