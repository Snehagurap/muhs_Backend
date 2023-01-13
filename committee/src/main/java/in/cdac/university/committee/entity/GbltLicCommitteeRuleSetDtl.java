package in.cdac.university.committee.entity;

import java.io.Serial;
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
@Table(name="gblt_lic_committee_ruleset_dtl", schema = "ucom")
@IdClass(GbltLicCommitteeRuleSetDtlPK.class)
public class GbltLicCommitteeRuleSetDtl implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumComRsDtlId;
	
	@Id
    private Integer unumIsValid; 
	
	@Column(name="unum_comrolsno")
	private Integer unumComrolsno;
	
	@Column(name="unum_com_rs_id")
	private Integer unumComRsId;
	
	@Column(name="unum_ctypeid ")
	private Integer unumCtypeid;
	
	@Column(name="unum_role_cfaculty_id")
	private Integer unumRoleCfacultyId;
	
	@Column(name="unum_role_stream_id")
	private Integer unumRoleStreamId;
	
	@Column(name="unum_role_is_external")
	private Integer unumRoleIsExternal;
	
	@Column(name="unum_staff_type_id")
	private Integer unumStaffTypeId;
	
	@Column(name="unum_role_department_id")
	private Integer unumRoleDepartmentId;
	
	@Column(name="unum_role_post_id")
	private Integer unumRolePostId;
	
	@Column(name="unum_role_min_exp_years")
	private Integer unumRoleMinExpYears;
	
	@Column(name="unum_role_max_exp_years")
	private Integer unumRoleMaxExpYears;
	
	@Column(name="unum_role_min_yearly_occurance")
	private Integer unumRoleMinYearlyOccurance;
	
	@Column(name="unum_role_max_yearly_occurance")
	private Integer unumRoleMaxYearlyOccurance;
	
	@Column(name="unum_vc_nominated_flag")
	private Integer unumVcNominatedFlag;
	
	@Column(name="unum_min_approved_exp")
	private Integer unumMinApprovedExp;
	
	@Column(name="unum_max_approved_exp")
	private Integer unumMaxApprovedExp;
	
	@Column(name="unum_exclude_col_zone")
	private Integer unumExcludeColZone;
	
	@Column(name="unum_exclude_col_district")
	private Integer unumExcludeColDistrict;
	
	@Column(name="unum_adjacent_district")
	private Integer unumAdjacentDistrict;
	
	@Column(name="ustr_comrole_description")
	private String ustrComroleDescription;
	
	@Column(name="unum_univ_id")
	private Integer unumUnivId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;
	
	@Column(name="unum_role_id")
	private Integer unumRoleId;
	
	@Column(name="unum_entry_uid")
	private Long unumEntryUid;
	
}
