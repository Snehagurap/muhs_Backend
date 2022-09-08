package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;


@Getter
@Setter
public class GmstCoursefacultyMstPK implements Serializable {

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}