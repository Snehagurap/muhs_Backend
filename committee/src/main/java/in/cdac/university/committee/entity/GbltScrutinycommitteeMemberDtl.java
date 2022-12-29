package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@IdClass(GbltScrutinycommitteeMemberDtlPK.class)
@Entity
@Table(name="gblt_scrutinycommittee_member_dtl", schema = "ucom")
public class GbltScrutinycommitteeMemberDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumScomMemberId;

	@Id
	private Integer unumIsvalid;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="udt_revalidation_date")
	private Date udtRevalidationDate;

	@Column(name="unum_ctypeid")
	private Integer unumCtypeid;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_formula_based")
	private Integer unumIsFormulaBased;

	@Column(name="unum_login_id")
	private Long unumLoginId;

	@Column(name="unum_scom_id")
	private Long unumScomId;

	@Column(name="unum_scom_member_sno")
	private Integer unumScomMemberSno;

	@Column(name="unum_scom_pref1_empid")
	private Long unumScomPref1Empid;

	@Column(name="unum_scom_pref1_empname")
	private String unumScomPref1Empname;

	@Column(name="unum_scom_pref2_empid")
	private Long unumScomPref2Empid;

	@Column(name="unum_scom_pref2_empname")
	private String unumScomPref2Empname;

	@Column(name="unum_scom_pref3_empid")
	private Long unumScomPref3Empid;

	@Column(name="unum_scom_pref3_empname")
	private String unumScomPref3Empname;

	@Column(name="unum_scom_pref4_empid")
	private Long unumScomPref4Empid;

	@Column(name="unum_scom_pref4_empname")
	private String unumScomPref4Empname;

	@Column(name="unum_scom_pref5_empid")
	private Long unumScomPref5Empid;

	@Column(name="unum_scom_pref5_empname")
	private String unumScomPref5Empname;

	@Column(name="unum_scom_role_id")
	private Long unumScomRoleId;

	@Column(name="unum_scom_rs_dtl_id")
	private Long unumScomRsDtlId;

	@Column(name="unum_scom_rs_id")
	private Long unumScomRsId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_pass")
	private String ustrPass;

	@Column(name="ustr_scom_description")
	private String ustrScomDescription;

	@Column(name="unum_scom_pref_other_empid")
	private String unumScomPrefOtherEmpid;

	@Column(name="unum_vc_nominated_flag")
	private String unumVcNominatedFlag;
}