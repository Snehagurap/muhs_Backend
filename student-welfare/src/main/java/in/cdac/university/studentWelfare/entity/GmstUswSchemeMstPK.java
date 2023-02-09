package in.cdac.university.studentWelfare.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GmstUswSchemeMstPK implements Serializable {

	@Column(name="unum_scheme_id")
	private Long 	unumSchemeId;
	
	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}
