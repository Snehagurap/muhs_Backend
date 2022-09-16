package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GmstNotificationTypeMstPK implements Serializable {

	@Column(name="unum_ntype_id")
	private Integer unumNtypeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}