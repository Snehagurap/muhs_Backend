package in.cdac.university.planningBoard.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GbltNotificationMasterPK implements Serializable {

	@Column(name="unum_nid")
	private Long unumNid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}