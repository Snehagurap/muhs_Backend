package in.cdac.university.committee.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class GbltLicCommitteeRuleSetDtlPK implements Serializable {

	@Column(name="unum_com_rs_dtl_id")
	private Long unumComRsDtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
	
}
