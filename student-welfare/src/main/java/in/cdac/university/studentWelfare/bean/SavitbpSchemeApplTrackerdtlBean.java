package in.cdac.university.studentWelfare.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SavitbpSchemeApplTrackerdtlBean {
	
	
	private Long 	unumSavitbpApplicationid;
	
	private Date 	unumSavitbpApplicationdt;
	
	private Long 	unumSchemeId;
	
	private Long 	unumCollegeId;
	
	private Long 	unumStudentId;
	
	private Integer	unumSno;
	
	private Long 	unumApplLevelid;
	
	private Long 	unumApplStatusid;
	
	private Long 	unumApplDecisionStatusid;
	
	private String 	ustrRemarks;
	
	private Integer unumDocId;
	
	private String 	ustrDocNo;
	
	private Date 	udtDocDate;
	
	private String 	ustrDocPath;
	
	private Integer unumUnivId;
	
	private String 	udtEffFrom;
	
	private Date 	udtEffTo;
	
	private String  ustrDescription;
	
	private Integer unumIsvalid;
	
	private Integer unumEntryUid;
	
	private String 	udtEntryDate;
	
	private Integer unumLstModUid;
	
	private Date 	udtLstModDate;
	
	private Integer	unumApprovalStatusid;

}
