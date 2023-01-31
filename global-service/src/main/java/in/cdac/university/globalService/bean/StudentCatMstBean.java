package in.cdac.university.globalService.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentCatMstBean {
	
	@ComboKey
	private Long unumStuCatId;
	
	@ComboValue
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
