package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltCommitteeDtlPK implements Serializable {

	@Column(name="unum_comroleid")
	private Long unumComroleid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}