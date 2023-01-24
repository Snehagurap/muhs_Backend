package in.cdac.university.studentWelfare.entity;

import java.io.Serializable;

import javax.persistence.Column;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GmstSwSavitbpSchemeApplPK implements Serializable {

	@Column(name="unum_savitbp_applicationid")
	private Long 	unumSavitbpApplicationid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}
