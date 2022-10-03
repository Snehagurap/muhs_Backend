package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstAffiliationstatusMstPK implements Serializable {

	@Column(name="unum_aff_status_id")
	private Long unumAffStatusId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}