package in.cdac.university.studentWelfare.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltConfigApplicationTrackerPK implements Serializable {
	//default serial version id, required for serializable classes.
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_application_id")
	private long unumApplicationId;

	@Column(name="unum_applicant_id")
	private long unumApplicantId;

	@Column(name="unum_nid")
	private long unumNid;

	@Column(name="unum_ndtl_id")
	private long unumNdtlId;

	@Column(name="unum_application_status_sno")
	private long unumApplicationStatusSno;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}