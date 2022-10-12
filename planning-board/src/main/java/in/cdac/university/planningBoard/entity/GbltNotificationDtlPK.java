package in.cdac.university.planningBoard.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GbltNotificationDtlPK implements Serializable {

	@Column(name="unum_ndtl_id")
	private Long unumNdtlId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}