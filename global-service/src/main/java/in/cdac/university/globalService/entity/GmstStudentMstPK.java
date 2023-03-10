package in.cdac.university.globalService.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GmstStudentMstPK implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_student_id")
	private Long	unumStudentId;
	
	@Column(name="unum_isvalid") 
	private Integer	unumIsvalid;
}
