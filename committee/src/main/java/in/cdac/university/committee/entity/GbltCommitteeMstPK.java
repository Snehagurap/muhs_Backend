package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltCommitteeMstPK implements Serializable {

	@Column(name="unum_comid")
	private Long unumComid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}