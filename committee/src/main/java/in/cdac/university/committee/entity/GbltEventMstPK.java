package in.cdac.university.committee.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GbltEventMstPK implements Serializable {

	@Column(name="unum_eventid")
	private Long unumEventid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}