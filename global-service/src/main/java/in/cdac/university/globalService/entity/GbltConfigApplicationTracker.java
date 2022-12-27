package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltConfigApplicationTrackerPK.class)
@Entity
@Table(name="gblt_config_application_tracker", schema = "templedata")
public class GbltConfigApplicationTracker implements Serializable {
	@Id
	private Long unumApplicationId;

	@Id
	private Long unumApplicantId;

	@Id
	private Long unumNid;

	@Id
	private Long unumNdtlId;

	@Id
	private Integer unumApplicationStatusSno;

	@Id
	private Integer unumIsvalid;

	@Column(name="decision_status_id")
	private Integer decisionStatusId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_application_date")
	private Date udtApplicationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_application_status_dt")
	private Date udtApplicationStatusDt;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_centre_govt_approval_date")
	private Date udtCentreGovtApprovalDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_centre_outdate")
	private Date udtCentreOutdate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_coa_date")
	private Date udtCoaDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_fta_date")
	private Date udtFtaDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_state_govt_approval_date")
	private Date udtStateGovtApprovalDate;

	@Column(name="udt_state_outdate")
	private String udtStateOutdate;

	@Column(name="unum_application_level_id")
	private Integer unumApplicationLevelId;

	@Column(name="unum_application_status_id")
	private Integer unumApplicationStatusId;

	@Column(name="unum_centre_govt_approval_type_id")
	private Integer unumCentreGovtApprovalTypeId;

	@Column(name="unum_coa_status_id")
	private Integer unumCoaStatusId;

	@Column(name="unum_ctype_id")
	private Integer unumCtypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Temporal(TemporalType.DATE)
	@Column(name="unum_lst_mod_dt")
	private Date unumLstModDt;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_ndtl_course_id")
	private Long unumNdtlCourseId;

	@Column(name="unum_ndtl_department_id")
	private Integer unumNdtlDepartmentId;

	@Column(name="unum_ndtl_faculty_id")
	private Integer unumNdtlFacultyId;

	@Column(name="unum_state_govt_approval_type_id")
	private Integer unumStateGovtApprovalTypeId;

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_centre_govt_approval_doc_path")
	private String ustrCentreGovtApprovalDocPath;

	@Column(name="ustr_centre_outno")
	private String ustrCentreOutno;

	@Column(name="ustr_coa_no")
	private String ustrCoaNo;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_fta_no")
	private String ustrFtaNo;

	@Column(name="ustr_state_govt_approval_doc_path")
	private String ustrStateGovtApprovalDocPath;

	@Column(name="ustr_state_outno_stategovt")
	private String ustrStateOutnoStategovt;

	@Column(name="ustr_status_by")
	private String ustrStatusBy;
}