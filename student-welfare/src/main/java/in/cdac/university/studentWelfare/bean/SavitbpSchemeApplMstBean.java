package in.cdac.university.studentWelfare.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SavitbpSchemeApplMstBean {

	
	private Long 	unumSavitbpApplicationid;
	
	private String 	unumSavitbpApplicationdt;
	
	private Long 	unumSchemeId;
	
	private Long 	unumCollegeId;
	
	private Long 	unumStudentId;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private String 	udtStuDob;
	
	private Integer unumGenderId;
	
	private String 	ustrStuAadhaarNo;
	
	private String 	ustrStuPanNo;
	
	private Long 	unumStuCatId;
	
	private Long 	unumStuSubCatId;
	
	private String  ustrCurAddress;
	
	private Integer unumCurStateId;
	
	private Integer unumCurDistId;
	
	private Integer unumCurPincd;
	
	private Long unumCurMobileNo; 
	
	private Long unumCurLandline;
	
	private String unumStuMailid;
	
	private String  ustrPerAddress;
	
	private Integer unumPerStateId;
	
	private Integer unumPerDistId;
	
	private Integer unumPerPincd;
	
	private String  ustrFatherFname;
	
	private String  ustrFatherAddress;
	
	private Integer unumFatherStateId;
	
	private Integer unumFatherDistId;
	
	private Integer unumFatherPincd;
	
	private Long unumFatherMobileno;
	
	private Long unumParentLandline;
	
	private String unumFatherMailid;
	
	private Integer unumFatherOccupationId;
	
	private String  ustrFatherofcAddress;
	
	private Integer unumFatherofcStateId;
	
	private Integer unumFatherofcDistId;
	
	private Integer unumFatherofcPincd;
	
	private Long unumFatherofcMobileno;
	
	private Long unumFatherofcLandline;
	
	private String unumFatherofcMailid;
	
	private String  ustrMotherFname;
	
	private String  ustrMotherAddress;
	
	private Integer unumMotherStateId;
	
	private Integer unumMotherDistId;
	
	private Integer unumMotherPincd;
	
	private Long unumMotherMobileno;
	
	private Long unumMorentLandline;
	
	private String unumMotherMailid;
	
	private Integer unumMotherOccupationId;
	
	private String  ustrMotherofcAddress;
	
	private Integer unumMotherofcStateId;
	
	private Integer unumMotherofcDistId;
	
	private Integer unumMotherofcPincd;
	
	private Long unumMotherofcMobileno;
	
	private Long unumMotherofcLandline;
	
	private String unumTherofcMailid;
	
	private Integer unumRelationshipId;
	
	private Double unumParentAnnualIncome;
	
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
	
	private String    udtEffFrom;
	
	private Date    udtEffTo;
	
	private Integer unumIsvalid;
	
	private Long unumEntryUid;
	
	private String    udtEntryDate;
	
	private Long unumLstModUid;
	
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
	
	private Long 	 unumPerMobileno ;
	
	private String   ustrPassingMarksheetPath;
	
	private String   udtPossibleCourseCompDt;
	
	private String 	 ustrPresentYear;
	
	private String 	 ustrAcademicYear;
	
	private Integer unumStuSalutationId;
	
	private Integer unumFatherSalutationId;
	
	private Integer unumMotherSalutationId;
	
	private Integer 	unumCourseId;
	
}
