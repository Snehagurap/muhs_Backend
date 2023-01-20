package in.cdac.university.committee.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@ToString
@Table(name="gblt_liccommittee_member_dtl", schema = "ucom")
@IdClass(GbltLicCommitteeMemberDtlPK.class)
public class GbltLicCommitteeMemberDtl {

	@Id
	private	Long unumLicMemberId;

	@Id
	private	Integer unumIsValid;

	@Column(name="unum_lic_id") 
	private	Long unumLicId ;

	@Column(name="unum_lic_rs_id")
	private	Long unumLicRsId ;

	@Column(name="unum_lic_rs_dtl_id") 
	private	Long unumLicRsDtlId ;

	@Column(name="unum_lic_member_sno") 
	private	Integer unumLicMemberSno ;

	@Column(name="unum_lic_role_id") 
	private	Integer unumLicRoleId ;

	@Column(name="unum_lic_pref1_empid") 
	private	Long unumLicPref1Empid ;

	@Column(name="unum_lic_pref2_empid") 
	private	Long unumLicPref2Empid ;

	@Column(name="unum_lic_pref3_empid") 
	private	Long unumLicPref3Empid ;

	@Column(name="unum_lic_pref4_empid") 
	private	Long unumLicPref4Empid ;

	@Column(name="unum_lic_pref5_empid") 
	private	Long unumLicPref5Empid ;

	@Column(name="unum_lic_pref1_empname") 
	private	String unumLicPref1Empname;

	@Column(name="unum_lic_pref2_empname")
	private	String unumLicPref2Empname;

	@Column(name="unum_lic_pref3_empname") 
	private	String unumLicPref3Empname;

	@Column(name="unum_lic_pref4_empname") 
	private	String unumLicPref4Empname;

	@Column(name="unum_lic_pref5_empname") 
	private	String unumLicPref5Empname;

	@Column(name="ustr_lic_description") 
	private	String ustrLicDescription;

	@Column(name="unum_univ_id") 
	private	Integer unumUnivId ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date") 
	private	Date udtEntryDate;
	
	@Column(name="unum_entry_uid") 
	private	Integer unumEntryUid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_revalidation_date") 
	private	Date udtRevalidationDate ;

	@Column(name="unum_is_formula_based")	
	private	Integer unumIsFormulaBased;

	@Column(name="unum_login_id") 
	private	Integer unumLoginId;

	@Column(name="ustr_pass") 
	private	String ustrPass  ;

	@Column(name="unum_ctypeid")
	private	Integer unumCtypeid;         


}
