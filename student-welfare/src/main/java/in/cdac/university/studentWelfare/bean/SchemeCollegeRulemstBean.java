package in.cdac.university.studentWelfare.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchemeCollegeRulemstBean {
	private Long 		unumSchemeId;
	private Long 		unumCourseTypeId;
	private Integer 	unumMinColIntake;
	private Integer 	unumMaxColIntake;
	private Integer 	unumNoOfBenificiaryAllowed;
	private String 		ustrDescription;
	private Date 		udtEffFrom;
	private Date 		udtEffTo;
	private Integer 	unumUnivId;
	private Date 		udtEntryDate;
	private Integer 	unumIsvalid;
	private Integer 	unumEntryUid;
	private Integer		unumLstModUid;
	private Date 		udtLstModDt;
	private Integer 	unumApprovalStatusid;
	private Integer 	unumAbsoluteOrPercentFlag;
}
