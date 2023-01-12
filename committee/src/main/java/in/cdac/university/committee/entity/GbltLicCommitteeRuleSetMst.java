package in.cdac.university.committee.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="gblt_lic_committee_ruleset_mst", schema = "ucom")
@IdClass(GbltLicCommitteeRuleSetMstPk.class)
public class GbltLicCommitteeRuleSetMst {

	@Id
	private Integer unumComRsId;
	
	@Id
	private Integer unumIsValid;
	
	@Column(name="unum_com_rs_cat_id")
	private Integer unumComRsCatId;
	
	@Column(name="unum_comduration_days")
	private Integer unumComdurationDays;
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_com_start_date")
	private Date udtComStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_com_end_date")
	private Date udtComEndDate;
	
	@Column(name="ustr_com_rs_name")
	private String ustrComRsName;
	
	@Column(name="unum_no_of_members")
	private Integer unumNoOfMembers;
	
	@Column(name="unum_com_cfaculty_id")
	private Integer unumComCfacultyId;
	
	@Column(name="ustr_com_foryear")
	private String ustrComForyear;
	
	@Column(name="ustr_com_formonth")
	private Integer ustrComFormonth;
	
	@Column(name="ustr_com_description")
	private String ustrComDescription;
	
	@Column(name="unum_univ_id")
	private Integer unumUnivId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;
	
	@Column(name="unum_entry_uid")
	private Integer unumEntryUid;
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_revalidation_date")
	private Date udtRevalidationDate;
	
	@Column(name="unum_is_formula_based")
	private Integer unumIsFormulaBased;
	
	@Column(name="unum_is_login_restricted")
	private Integer unumIsLongRestricted;
	
	@Column(name="unum_stream_id")
	private Integer unumStreamId;
	
	@Column(name="ustr_ctypeids")
	private String ustrCtypeids;
}
