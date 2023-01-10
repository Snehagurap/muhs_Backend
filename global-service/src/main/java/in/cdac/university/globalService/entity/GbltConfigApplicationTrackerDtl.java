package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gblt_config_application_tracker_dtl")
@IdClass(GbltConfigApplicationTrackerDtlPK.class)
public class GbltConfigApplicationTrackerDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private long unumApplicationId;

	@Id
	private long unumApplicantId;

	@Id
	private long unumNid;

	@Id
	private long unumNdtlId;

	@Id
	private long unumApplicationStatusSno;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_application_date")
	private Date udtApplicationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_coa_date")
	private Date udtCoaDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_doc_date")
	private Date udtDocDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_ec_date")
	private Date udtEcDate;

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
	@Column(name="udt_gr_date")
	private Date udtGrDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_loi_date")
	private Date udtLoiDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lop_date")
	private Date udtLopDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_noc_date")
	private Date udtNocDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_out_date")
	private Date udtOutDate;

	@Column(name="unum_application_doc_id")
	private Long unumApplicationDocId;

	@Column(name="unum_application_level_id")
	private Integer unumApplicationLevelId;

	@Column(name="unum_application_status_id")
	private Integer unumApplicationStatusId;

	@Column(name="unum_approval_statusid")
	private Integer unumApprovalStatusid;

	@Column(name="unum_decision_status_id")
	private Integer unumDecisionStatusId;

	@Column(name="unum_doc_sno")
	private Integer unumDocSno;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Temporal(TemporalType.DATE)
	@Column(name="unum_lst_mod_date")
	private Date unumLstModDate;

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

	@Column(name="unum_out_no")
	private String unumOutNo;

	@Column(name="unum_recommendedfor_intake")
	private Integer unumRecommendedforIntake;

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_application_doc_no")
	private String ustrApplicationDocNo;

	@Column(name="ustr_coa_no")
	private String ustrCoaNo;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_doc_name")
	private String ustrDocName;

	@Column(name="ustr_doc_path")
	private String ustrDocPath;

	@Column(name="ustr_ec_no")
	private String ustrEcNo;

	@Column(name="ustr_fta_no")
	private String ustrFtaNo;

	@Column(name="ustr_gr_no")
	private String ustrGrNo;

	@Column(name="ustr_loi_no")
	private String ustrLoiNo;

	@Column(name="ustr_lop_no")
	private String ustrLopNo;

	@Column(name="ustr_noc_no")
	private String ustrNocNo;

	@Column(name="ustr_remarks")
	private String ustrRemarks;

	@Column(name="ustr_status_by")
	private String ustrStatusBy;

}