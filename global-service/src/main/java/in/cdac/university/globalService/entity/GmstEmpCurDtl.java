package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstEmpCurDtlPK.class)
@Table(name="gmst_emp_cur_dtl", schema = "university")
public class GmstEmpCurDtl implements Serializable {
	@Id
	private Long unumEmpCurId;

	@Id
	private Integer unumIsvalid;

	@Column(name="authority_bos_acadcounsil_mgtcounsil_senate")
	private String authorityBosAcadcounsilMgtcounsilSenate;

	@Column(name="lictl_acadyear_child")
	private String lictlAcadyearChild;

	@Column(name="lictl_desigpg")
	private String lictlDesigpg;

	@Column(name="lictl_desigug")
	private String lictlDesigug;

	@Column(name="lictl_expyears_pg")
	private String lictlExpyearsPg;

	@Column(name="lictl_expyears_ug")
	private String lictlExpyearsUg;

	@Column(name="lictl_rank")
	private String lictlRank;

	@Column(name="lictl_selectionforaction")
	private String lictlSelectionforaction;

	@Column(name="lictl_selectionpriority")
	private String lictlSelectionpriority;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_from_date")
	private Date udtFromDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_pg_joining_date")
	private Date udtPgJoiningDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_to_date")
	private Date udtToDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_ug_joining_date")
	private Date udtUgJoiningDate;

	@Column(name="unum_authority_roleid")
	private Long unumAuthorityRoleid;

	@Column(name="unum_desigid_pg")
	private Long unumDesigidPg;

	@Column(name="unum_desigid_ug")
	private Long unumDesigidUg;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_rank")
	private Integer unumRank;

	@Column(name="unum_selectforaction_roleid")
	private Long unumSelectforactionRoleid;

	@Column(name="unum_selection_priority_id")
	private Long unumSelectionPriorityId;

	@Column(name="unum_selectionpriority_roleid")
	private Long unumSelectionpriorityRoleid;

	@Column(name="unum_emp_desigid")
	private Integer unumEmpDesigid;

	@Column(name="unum_emp_id")
	private Long unumEmpId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_remarks")
	private String ustrRemarks;

	@Column(name="ustr_t_aadhar_no")
	private String ustrTAadharNo;

}