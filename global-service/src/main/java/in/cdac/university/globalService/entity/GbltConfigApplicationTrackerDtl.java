package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltConfigApplicationTrackerDtlPK.class)
@Entity
@Table(name="gblt_config_application_tracker_dtl", schema = "templedata")
public class GbltConfigApplicationTrackerDtl implements Serializable {

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

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_application_status_id")
	private Integer unumApplicationStatusId;

	@Column(name="unum_doc_sno")
	private Long unumDocSno;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_ndtl_course_id")
	private Long unumNdtlCourseId;

	@Column(name="unum_ndtl_department_id")
	private Long unumNdtlDepartmentId;

	@Column(name="unum_ndtl_faculty_id")
	private Long unumNdtlFacultyId;

	@Column(name="unum_recommendedfor_intake")
	private Long unumRecommendedforIntake;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_doc_name")
	private String ustrDocName;

	@Column(name="ustr_doc_path")
	private String ustrDocPath;

	@Column(name="ustr_remarks")
	private String ustrRemarks;

	@Column(name="ustr_status_by")
	private String ustrStatusBy;
}