package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GmstCommitteeRoleMstPK implements Serializable {

	@Column(name="unum_role_id")
	private Integer unumRoleId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}