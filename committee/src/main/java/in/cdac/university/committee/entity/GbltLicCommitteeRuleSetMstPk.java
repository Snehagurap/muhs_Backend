package in.cdac.university.committee.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class GbltLicCommitteeRuleSetMstPk implements Serializable {

	@Column(name="unum_com_rs_id")
	private Integer unumComRsId;

	@Column(name="unum_isvalid")
	private Integer unumIsValid;
	
}
