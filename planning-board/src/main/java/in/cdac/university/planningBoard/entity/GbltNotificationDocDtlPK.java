package in.cdac.university.planningBoard.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GbltNotificationDocDtlPK implements Serializable {

	@Column(name="unum_ndocid")
	private Long unumNdocid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}