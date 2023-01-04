package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GmstAdjacentDistrictDtlPK implements Serializable {
	//default serial version id, required for serializable classes.
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="num_dist_id")
	private Integer numDistId;

	@Column(name="num_adjacent_dist_id")
	private Integer numAdjacentDistId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}