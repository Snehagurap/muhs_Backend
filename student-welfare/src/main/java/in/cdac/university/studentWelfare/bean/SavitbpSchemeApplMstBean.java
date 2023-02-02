package in.cdac.university.studentWelfare.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SavitbpSchemeApplMstBean {

	
	private Long 	unumSavitbpApplicationid;
	
	private Date 	unumSavitbpApplicationdt;
	
	private Long 	unumSchemeId;
	
	private Long 	unumCollegeId;
	
	private Long 	unumStudentId;
	
	private Date 	udtStuDob;
	
	private Integer unumGenderId;
	
	private String 	ustrStuAadhaarNo;
	
	private String 	ustrStuPanNo;
	
	private Long 	unumStuCatId;
	
	private Long 	unumStuSubCatId;
	
	private String  ustrCurAddress;
	
	private Integer unumCurStateId;
	
	private Integer unumCurDistId;
	
	private Integer unumCurPincd;
	
	private Integer unumCurMobileno;
	
	private Integer unumCurLandline;
	
	private Integer unumStuMailid;
	
	private String  ustrPerAddress;
	
	private Integer unumPerStateId;
	
	private Integer unumPerDistId;
	
	private Integer unumPerPincd;
	
	private String  ustrFatherFname;
	
	private String  ustrFatherAddress;
	
	private Integer unumFatherStateId;
	
	private Integer unumFatherDistId;
	
	private Integer unumFatherPincd;
	
	private Integer unumFatherMobileno;
	
	private Integer unumParentLandline;
	
	private Integer unumFatherMailid;
	
	private Integer unumFatherOccupationId;
	
	private String  ustrFatherofcAddress;
	
	private Integer unumFatherofcStateId;
	
	private Integer unumFatherofcDistId;
	
	private Integer unumFatherofcPincd;
	
	private Integer unumFatherofcMobileno;
	
	private Integer unumFatherofcLandline;
	
	private Integer unumFatherofcMailid;
	
	private String  ustrMotherFname;
	
	private String  ustrMotherAddress;
	
	private Integer unumMotherStateId;
	
	private Integer unumMotherDistId;
	
	private Integer unumMotherPincd;
	
	private Integer unumMotherMobileno;
	
	private Integer unumMorentLandline;
	
	private Integer unumMotherMailid;
	
	private Integer unumMotherOccupationId;
	
	private String  ustrMotherofcAddress;
	
	private Integer unumMotherofcStateId;
	
	private Integer unumMotherofcDistId;
	
	private Integer unumMotherofcPincd;
	
	private Integer unumMotherofcMobileno;
	
	private Integer unumMotherofcLandline;
	
	private Integer unumTherofcMailid;
	
	private Integer unumRelationshipId;
	
	private Integer unumParentAnnualIncome;
	
	private String  ustrStudentBankaccountName;
	
	private String  ustrBankName;
	
	private String  ustrBankAddress;
	
	private Integer unumBankStateId;
	
	private Integer unumBankDistId;
	
	private Integer unumBankPincd;
	
	private Integer unumBankAccountno;
	
	private String  ustrIfsCode;
	
	private String  ustrStuPhotoPath;
	
	private String  ustrIncomeProofPath;
	
	private String  ustrCasteCertificatePath;
	
	private String  ustrAadhaarPath;
	
	private Integer unumUnivId;
	
	private Date    udtEffFrom;
	
	private Date    udtEffTo;
	
	private Integer unumIsvalid;
	
	private Long unumEntryUid;
	
	private Date    udtEntryDate;
	
	private Integer unumLstModUid;
	
	private Date    udtLstModDate;
	
	private Integer unumApprovalStatusid;      
	
	private Integer	unumIsNri ;
	
	private Integer	unumIsIntern;
	
	private Integer	unumIsOms ;
	
	private Integer	unumIsStipendearner;
	
	private Integer	unumIsManagementQuota;
	
	private String	 ustrEnrollmentNo;
	
	private String	 ustrStudentFname;
	
	private String	 ustrStudentLname;
	
	private String 	 ustrPerContactno;
	
	private Integer  unumPerMobileno ;
	
	private String   ustrPassingMarksheetPath;
}
