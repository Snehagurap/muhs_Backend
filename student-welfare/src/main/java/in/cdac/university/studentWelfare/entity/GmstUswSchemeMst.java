package in.cdac.university.studentWelfare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstUswSchemeMstPK.class)
@Entity
@Table(name="gmst_usw_scheme_mst", schema = "sw")

public class GmstUswSchemeMst {

		@Id   
		private Long 	unumSchemeId;
		
		@Id
		private Integer unumIsvalid;
		
		@Column(name="ustr_scheme_code")
		private String  ustrSchemeCode;
		
		@Column(name="ustr_scheme_sname")
		private String  ustrSchemeSname;
		
		@Column(name="ustr_scheme_fname")
		private String  ustrSchemeFname;
		
		@Column(name="unum_scheme_typeid")
		private Integer unumSchemeTypeid;
		
		@Column(name="unum_scheme_purposeid")
		private Integer unumSchemePurposeid;
		
		@Column(name="unum_max_beneficiaries_university")
		private Integer unumMaxBeneficiariesUniversity;
		
		@Column(name="unum_max_beneficiaries_percolege")
		private Integer unumMaxBeneficiariesPercolege;
		
		@Column(name="unum_max_amount_university")
		private Integer unumMaxAmountUniversity;
		
		@Column(name="unum_max_amount_percolege")
		private Integer unumMaxAmountPercolege;
		
		@Column(name="unum_max_amount_perbeneficiary")
		private Integer unumMaxAmountPerbeneficiary;
		
		@Column(name="ustr_eligible_coursetypeids")
		private String  ustrEligibleCoursetypeids;
		
		@Column(name="unum_coursetype_limittype_flag")
		private Integer unumCoursetypeLimittypeFlag;
		
		@Column(name="unum_ug_limit_university")
		private Integer unumUgLimitUniversity;
		
		@Column(name="unum_pg_limit_university")
		private Integer unumPgLimitUniversity;
		
		@Column(name="unum_intern_limit_university")
		private Integer unumInternLimitUniversity;
		
		@Column(name="unum_oms_limit_university")
		private Integer unumOmsLimitUniversity;
		
		@Column(name="ustr_elegible_faculties")
		private Integer ustrElegibleFaculties;
		
		@Column(name="ustr_elegible_streams")
		private Integer ustrElegibleStreams;
		
		@Column(name="unum_application_fees_required")
		private Integer unumApplicationFeesRequired;
		
		@Column(name="unum_gender_specific")
		private Integer unumGenderSpecific;
		
		@Column(name="unum_student_type")
		private Integer unumStudentType;
		
		@Column(name="unum_beneficiary_max_income")
		private Integer unumBeneficiaryMaxIncome;
		
		@Column(name="unum_is_medical_related")
		private Integer unumIsMedicalRelated;
		
		@Column(name="unum_faculty_id")
		private Long	unumFacultyId;
		
		@Column(name="unum_stream_id")
		private Long 	unumStreamId;
		
		@Column(name="unum_course_id")
		private Long 	unumCourseId;
		
		@Column(name="unum_min_col_intake_notinuse")
		private Integer unumMinColIntakeNotinuse;
		
		@Column(name="unum_max_col_intake_notinuse")
		private Integer unumMaxColIntakeNotinuse;
		
		@Column(name="ustr_description")
		private String  ustrDescription;
		
		@Column(name="udt_eff_from")
		private Date 	udtEffFrom;
		
		@Column(name="udt_eff_to")
		private Date 	udtEffTo;
		
		@Column(name="unum_univ_id")
		private Integer unumUnivId;
		
		@Column(name="udt_entry_date")
		private Long 	udtEntryDate;
				
		@Column(name="unum_entry_uid")
		private Integer unumEntryUid;
		
		@Column(name="unum_lst_mod_uid")
		private Integer unumLstModUid;
		
		@Column(name="udt_lst_mod_dt")
		private Date 	udtLstModDt;
		
		@Column(name="unum_approval_statusid")
		private Integer unumApprovalStatusid;
		
		@Column(name="unum_intern_eligible_flag")
		private Integer unumInternEligibleFlag;
		
		@Column(name="unum_nri_eligible_flag")
		private Integer unumNriEligibleFlag;
		
		@Column(name="unum_oms_eligible_flag")
		private Integer unumOmsEligibleFlag;
		
		@Column(name="unum_stipend_earner_eligible_flag")
		private Integer unumStipendEarnerEligibleFlag;
		
		@Column(name="unum_managementquota_eligible_flag")
		private Integer unumManagementquotaEligibleFlag;
		
		@Column(name="unum_scheme_for")
		private Integer unumSchemeFor;
		
		@Column(name="unum_payment_to")
		private Integer unumPaymentTo;














}
