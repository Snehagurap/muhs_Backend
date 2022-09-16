package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
public class GmstCollegeMstPK implements Serializable {

	@Column(name="unum_college_id")
	private Long unumCollegeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}