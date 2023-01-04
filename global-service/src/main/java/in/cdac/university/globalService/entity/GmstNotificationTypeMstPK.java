package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstNotificationTypeMstPK implements Serializable {

	//@Column(name="unum_ntype_id")
	@Column(name="unum_npurpose_id")
	private Integer unumNtypeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}