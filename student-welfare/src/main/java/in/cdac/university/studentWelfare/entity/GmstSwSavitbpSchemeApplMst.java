package in.cdac.university.studentWelfare.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstSwSavitbpSchemeApplPK.class)
@Entity
@Table(name="gmst_sw_savitbp_scheme_appl_mst", schema = "sw")
public class GmstSwSavitbpSchemeApplMst {
	
	@Id
	private Long 	unumSavitbpApplicationid;

	@Id
	private Integer unumIsvalid;

	@Id
	private Long 	unumSchemeId;
	
	@Id
	private Long 	unumCollegeId;
	
	@Id
	private Long 	unumStudentId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="unum_savitbp_applicationdt")
	private Date 	unumSavitbpApplicationdt;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="udt_stu_dob")
	private Date 	udtStuDob;

	@Column(name="unum_gender_id")
	private Integer unumGenderId;

	@Column(name="ustr_stu_aadhaar_no")
	private String 	ustrStuAadhaarNo;

	@Column(name="ustr_stu_pan_no")
	private String 	ustrStuPanNo;

	@Column(name="unum_stu_cat_id")
	private Long 	unumStuCatId;

	@Column(name="unum_stu_sub_cat_id")
	private Long 	unumStuSubCatId;

	@Column(name="ustr_cur_address")
	private String  ustrCurAddress;

	@Column(name="unum_cur_state_id")
	private Integer unumCurStateId;

	@Column(name="unum_cur_dist_id")
	private Integer unumCurDistId;

	@Column(name="unum_cur_pincd")
	private Integer unumCurPincd;

	@Column(name="unum_cur_mobileno")
	private Long unumCurMobileNo;

	@Column(name="unum_cur_landline")
	private Long unumCurLandline;

	@Column(name="unum_stu_mailid")
	private String unumStuMailid;

	@Column(name="ustr_per_address")
	private String  ustrPerAddress;

	@Column(name="unum_per_state_id")
	private Integer unumPerStateId;

	@Column(name="unum_per_dist_id")
	private Integer unumPerDistId;

	@Column(name="unum_per_pincd")
	private Integer unumPerPincd;

	@Column(name="ustr_father_fname")
	private String  ustrFatherFname;

	@Column(name="ustr_father_address")
	private String  ustrFatherAddress;

	@Column(name="unum_father_state_id")
	private Integer unumFatherStateId;

	@Column(name="unum_father_dist_id")
	private Integer unumFatherDistId;

	@Column(name="unum_father_pincd")
	private Integer unumFatherPincd;

	@Column(name="unum_father_mobileno")
	private Long unumFatherMobileno;

	@Column(name="unum_parent_landline")
	private Long unumParentLandline;

	@Column(name="unum_father_mailid")
	private String unumFatherMailid;

	@Column(name="unum_father_occupation_id")
	private Integer unumFatherOccupationId;

	@Column(name="ustr_fatherofc_address")
	private String  ustrFatherofcAddress;

	@Column(name="unum_fatherofc_state_id")
	private Integer unumFatherofcStateId;

	@Column(name="unum_fatherofc_dist_id")
	private Integer unumFatherofcDistId;

	@Column(name="unum_fatherofc_pincd")
	private Integer unumFatherofcPincd;

	@Column(name="unum_fatherofc_mobileno")
	private Long unumFatherofcMobileno;

	@Column(name="unum_fatherofc_landline")
	private Long unumFatherofcLandline;

	@Column(name="unum_fatherofc_mailid")
	private String unumFatherofcMailid;

	@Column(name="ustr_mother_fname")
	private String  ustrMotherFname;

	@Column(name="ustr_mother_address")
	private String  ustrMotherAddress;

	@Column(name="unum_mother_state_id")
	private Integer unumMotherStateId;

	@Column(name="unum_mother_dist_id")
	private Integer unumMotherDistId;

	@Column(name="unum_mother_pincd")
	private Integer unumMotherPincd;

	@Column(name="unum_mother_mobileno")
	private Long unumMotherMobileno;

	@Column(name="unum_morent_landline")
	private Long unumMorentLandline;

	@Column(name="unum_mother_mailid")
	private String unumMotherMailid;

	@Column(name="unum_mother_occupation_id")
	private Integer unumMotherOccupationId;

	@Column(name="ustr_motherofc_address")
	private String  ustrMotherofcAddress;

	@Column(name="unum_motherofc_state_id")
	private Integer unumMotherofcStateId;

	@Column(name="unum_motherofc_dist_id")
	private Integer unumMotherofcDistId;

	@Column(name="unum_motherofc_pincd")
	private Integer unumMotherofcPincd;

	@Column(name="unum_motherofc_mobileno")
	private Long unumMotherofcMobileno;

	@Column(name="unum_motherofc_landline")
	private Long unumMotherofcLandline;

	@Column(name="unum_therofc_mailid")
	private String unumTherofcMailid;

	@Column(name="unum_relationship_id")
	private Integer unumRelationshipId;

	@Column(name="unum_parent_annual_income")
	private Double unumParentAnnualIncome;

	@Column(name="ustr_student_bankaccount_name")
	private String  ustrStudentBankaccountName;

	@Column(name="ustr_bank_name")
	private String  ustrBankName;

	@Column(name="ustr_bank_address")
	private String  ustrBankAddress;

	@Column(name="unum_bank_state_id")
	private Integer unumBankStateId;

	@Column(name="unum_bank_dist_id")
	private Integer unumBankDistId;

	@Column(name="unum_bank_pincd")
	private Integer unumBankPincd;

	@Column(name="unum_bank_accountno")
	private Integer unumBankAccountno;

	@Column(name="ustr_ifs_code")
	private String  ustrIfsCode;

	@Column(name="ustr_stu_photo_path")
	private String  ustrStuPhotoPath;

	@Column(name="ustr_income_proof_path")
	private String  ustrIncomeProofPath;

	@Column(name="ustr_caste_certificate_path")
	private String  ustrCasteCertificatePath;

	@Column(name="ustr_aadhaar_path")
	private String  ustrAadhaarPath;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date    udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date    udtEffTo;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date    udtEntryDate;

	@Column(name="unum_lst_mod_uid")
	private Integer unumLstModUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date    udtLstModDate;

	@Column(name="unum_approval_statusid")
	private Integer unumApprovalStatusid;  
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_possible_course_comp_dt")
	private Date udtPossibleCourseCompDt;
	
	@Column(name="ustr_present_year")
	private String ustrPresentYear;
	
	@Column(name="ustr_academic_year")
	private String ustrAcademicYear;
	
}
