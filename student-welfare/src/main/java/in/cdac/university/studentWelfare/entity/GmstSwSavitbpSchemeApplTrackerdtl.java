package in.cdac.university.studentWelfare.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstSwSavitbpSchemeApplTrackerdtlPK.class)
@Entity
@Table(name="gmst_sw_savitbp_scheme_appl_trackerdtl", schema = "sw")

public class GmstSwSavitbpSchemeApplTrackerdtl {
	
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

	@Id
	private Integer 	unumSno;
	
	@Temporal(TemporalType.DATE)
	@Column(name="unum_savitbp_applicationdt")
	private Date 	unumSavitbpApplicationdt;

	@Column(name="unum_appl_levelid")
	private Long 	unumApplLevelid;

	@Column(name="unum_appl_statusid")
	private Long 	unumApplStatusid;

	@Column(name="unum_appl_decision_statusid")
	private Long 	unumApplDecisionStatusid;

	@Column(name="ustr_remarks")
	private String 	ustrRemarks;

	@Column(name="unum_doc_id")
	private Integer unumDocId;

	@Column(name="ustr_doc_no")
	private String 	ustrDocNo;

	@Column(name="udt_doc_date")
	private Date 	udtDocDate;

	@Column(name="ustr_doc_path")
	private String 	ustrDocPath;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date 	udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date 	udtEffTo;

	@Column(name="ustr_description")
	private String  ustrDescription;

	@Column(name="unum_entry_uid")
	private Integer unumEntryUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date 	udtEntryDate;

	@Column(name="unum_lst_mod_uid")
	private Integer unumLstModUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date 	udtLstModDate;

	@Column(name="unum_approval_statusid")
	private Integer	unumApprovalStatusid;
	

}
