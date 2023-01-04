package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GbltScrutinycommitteeMemberDtlPK implements Serializable {
	//default serial version id, required for serializable classes.
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_scom_member_id")
	private Long unumScomMemberId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}