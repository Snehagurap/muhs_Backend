package in.cdac.university.globalService.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentSubcatMstBean {
	
	@NotNull(message = "Student Sub Cat ID is mandatory")
	private Long 	unumStuSubCatId;
	
	@NotNull(message = "Student Cat ID is mandatory")
	private Long 	unumStuCatId;
	
	private String  ustrStuSubCatSname;
	
	private String  ustrStuSubCatFname;
	
	private Integer unumUnivId;
	
	private Date 	udtEffFrom;
	
	private Date    udtEffTo;
	
	private Integer unumIsvalid;
	
	private Integer unumEntryUid;
	
	private Date    udtEntryDate;
	
	private Integer unumLstModUid;
	
	private Date    udtLstModDate;
}
