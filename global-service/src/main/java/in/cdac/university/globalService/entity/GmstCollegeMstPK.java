package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstCollegeMstPK implements Serializable {

	@Column(name="unum_college_id")
	private Long unumCollegeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}