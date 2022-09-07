package in.cdac.university.committee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GbltCommitteeTypeMstPK implements Serializable {

	@Column(name="unum_comtype_id")
	private Integer unumComtypeId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}