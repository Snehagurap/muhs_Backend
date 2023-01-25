package in.cdac.university.studentWelfare.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GmstSwSavitbpSchemeApplTrackermstPK implements Serializable {
	
	@Column(name="unum_savitbp_applicationid")    
	private Long unumSavitbpApplicationid;
	
	@Column(name="unum_scheme_id")  
	private Long unumSchemeId;
	
	@Column(name="unum_college_id")  
	private Long unumCollegeId;
	
	@Column(name="unum_student_id")  
	private Long unumStudentId;
	
	@Column(name="unum_sno")  
	private Integer unumSno;
	
	@Column(name="unum_isvalid")  
	private Integer unumIsvalid;
}
