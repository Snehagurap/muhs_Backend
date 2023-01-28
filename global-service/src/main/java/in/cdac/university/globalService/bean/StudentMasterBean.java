package in.cdac.university.globalService.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentMasterBean {
	
	@NotNull(message = "Student ID is mandatory")
	private Long	unumStudentId;
	
	private Date	udtStuDob ;
	
	private Integer	unumGenderId;
	
	private String	ustrStuAadhaarNo;
	
	private String	ustrStuPanNo;
	
	private Long	unumStuCatId;
	
	private Long	unumStuSubCatId;
	
	private String	ustrCurAddress ;
	
	private Integer	unumCurStateId ;
	
	private Integer	unumCurDistId ;
	
	private Integer	unumCurPincd ;
	
	private Integer	unumCurMobileNo; 
	
	private Integer	unumCurLandLine;
	
	private String	unumStuMailId ;
	
	private String	ustrPerAddress ;
	
	private Integer	unumPerStateId ;
	
	private Integer	unumPerDistId ;
	
	private Integer	unumPerPincd ;
	
	private String	ustrFatherFname; 
	
	private String	ustrFatherAddress; 
	
	private Integer	unumFatherStateId ;
	
	private Integer	unumFatherDistId ;
	
	private Integer	unumFatherPincd ;
	
	private Integer	unumFatherMobileNo;
	
	private Integer	unumParentLandLine;
	
	private String	unumFatherMailId ;
	
	private Integer	unumFatherOccupationId; 
	
	private String	ustrFatherofcAddress ;
	
	private Integer	unumFatherofcStateId ;
	
	private Integer	unumFatherofcDistId ;
	
	private Integer	unumFatherofcPincd ;
	
	private Integer	unumFatherofcMobileno; 
	
	private Long	unumFatherofcLandline ;
	
	private String	unumFatherofcMailid ;
	
	private String	ustrMotherFname ;
	
	private String	ustrMotherAddress;
	
	private Integer	unumMotherStateId;
	
	private Integer	unumMotherDistId ;
	
	private Integer	unumMotherPincd ;
	
	private Integer	unumMotherMobileno;
	
	private Long	unumMorentLandline;
	
	private String	unumMotherMailid ;
	
	private Integer	unumMotherOccupationId;
	
	private String	ustrMotherofcAddress ;
	
	private Integer	unumMotherofcStateId ;
	
	private Integer	unumMotherofcDistId ;
	
	private Integer	unumMotherofcPincd ;
	
	private Integer	unumMotherofcMobileno; 
	
	private Long	unumMotherofcLandline ;
	
	private String	unumTherofcMailid ;
	
	private Integer	unumRelationshipId ;
	
	private Double	unumParentAnnualIncome; 
	
	private String	ustrStudentBankaccountName; 
	
	private String	ustrBankName ;
	
	private String	ustrBankAddress ;
	
	private Integer	unumBankStateId ;
	
	private Integer	unumBankDistId ;
	
	private Integer	unumBankPincd ;
	
	private Long	unumBankAccountno; 
	
	private String	ustrIfsCode ;
	
	private Integer	unumUnivId ;
	
	private Date	udtEffFrom ;
	
	private Date	udtEffTo ;
	
	private Integer	unumIsvalid;
	
	private Integer	unumEntryUid;
	
	private Date	udtEntryDate;
	
	private Integer	unumLstModUid;
	
	private Date	udtLstModDate;
	
	private String	ustrStuPhotoPath;
	
	private String	ustIncomeProofPath;
	
	private String	ustrCasteCertificatePath;
	
	private String	ustrAadhaarPath ;
	
	private Integer	unumStudentTypeId;
	
	private Integer	unumIsNri ;
	
	private Integer	unumIsIntern;
	
	private Integer	unumIsOms ;
	
	private Integer	unumIsStipendearner;
	
	private Integer	unumIsManagementQuota;
}
