package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltCommitteeRulesetMstPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_com_rs_id")
	private Long unumComRsId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}