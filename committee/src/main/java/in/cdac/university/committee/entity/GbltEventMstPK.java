package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GbltEventMstPK implements Serializable {

	@Column(name="unum_eventid")
	private Long unumEventid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}