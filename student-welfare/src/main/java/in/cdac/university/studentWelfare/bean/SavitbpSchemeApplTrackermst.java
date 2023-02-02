package in.cdac.university.studentWelfare.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SavitbpSchemeApplTrackermst {
			
	private Long unumSavitbpApplicationid;
	
	private Date unumSavitbpApplicationdt;
	
	private Long unumSchemeId;
	
	private Long unumCollegeId;
	
	private Long unumStudentId;
	
	private Integer unumSno;
	
	private Long unumApplLevelid;
	
	private Long unumApplStatusid;
	
	private Long unumApplDecisionStatusid;
	
	private String ustrRemarks;
	
	private Integer unumUnivId;
	
	private Date udtEffFrom;
	
	private Date udtEffTo;
	
	private String ustrDescription;
	
	private Integer unumIsvalid;
	
	private Long unumEntryUid;
	
	private Date udtEntryDate;
	
	private Integer unumLstModUid;
	
	private Date udtLstModDate;
	
	private Integer unumApprovalStatusid;

}
