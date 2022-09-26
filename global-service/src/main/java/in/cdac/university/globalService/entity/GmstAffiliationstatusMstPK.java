package in.cdac.university.globalService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GmstAffiliationstatusMstPK implements Serializable {

	@Column(name="unum_aff_status_id")
	private Long unumAffStatusId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}