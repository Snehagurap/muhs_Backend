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
@IdClass(GmstUswSchemeCollegeRulemstPK.class)
@Entity
@Table(name="gmst_usw_scheme_college_rulemst", schema = "sw")

public class GmstUswSchemeCollegeRulemst {

	@Id 
	private Long 		unumSchemeId;
	
	@Id 
	private Long 		unumCourseTypeId;
	
	@Column(name="unum_min_col_intake") 
	private Integer 	unumMinColIntake;
	
	@Column(name="unum_max_col_intake") 
	private Integer 	unumMaxColIntake;
	
	@Column(name="unum_no_of_benificiary_allowed") 
	private Integer 	unumNoOfBenificiaryAllowed;
	
	@Column(name="ustr_description") 
	private String 		ustrDescription;
	
	@Column(name="udt_eff_from") 
	private Date 		udtEffFrom;
	
	@Column(name="udt_eff_to") 
	private Date 		udtEffTo;
	
	@Column(name="unum_univ_id") 
	private Integer 	unumUnivId;
	
	@Column(name="udt_entry_date") 
	private Date 		udtEntryDate;
	
	@Column(name="unum_isvalid") 
	private Integer 	unumIsvalid;
	
	@Column(name="unum_entry_uid") 
	private Integer 	unumEntryUid;
	
	@Column(name="unum_lst_mod_uid") 
	private Integer		unumLstModUid;
	
	@Column(name="udt_lst_mod_dt") 
	private Date 		udtLstModDt;
	
	@Column(name="unum_approval_statusid")
	private Integer 	unumApprovalStatusid;
	
	@Column(name="unum_absolute_or_percent_flag") 
	private Integer 	unumAbsoluteOrPercentFlag;
}

