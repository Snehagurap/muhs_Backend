package in.cdac.university.globalService.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StuCatMst {
			
	private Long unumStuCatId;
	
	private String  ustrStuCatSname;
	
	private String ustrStuCatFname;
	
	private Integer unumUnivId;
	
	private Date udtEffFrom;
	
	private Date udtEffTo;
	
	private Integer unumIsvalid;
	
	private Integer unumEntryUid;
	
	private Date udtEntryDate;
	
	private Integer unumLstModUid;
	
	private Date udtLstModDate;
}
