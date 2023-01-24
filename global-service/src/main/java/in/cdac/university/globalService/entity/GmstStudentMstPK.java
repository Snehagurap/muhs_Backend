package in.cdac.university.globalService.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class GmstStudentMstPK implements Serializable {
	
	@Column(name="unum_student_id")
	private Long	unumStudentId;
	
	@Column(name="unum_isvalid") 
	private Integer	unumIsvalid;
}
