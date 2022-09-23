package in.cdac.university.committee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GbltEventtypeMstPK implements Serializable {

	@Column(name="unum_event_typeid")
	private Long unumEventTypeid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}