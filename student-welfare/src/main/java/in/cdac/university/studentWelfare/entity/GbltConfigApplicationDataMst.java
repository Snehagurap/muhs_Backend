package in.cdac.university.studentWelfare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GbltConfigApplicationDataMstPK.class)
@Table(name="gblt_config_application_data_mst", schema = "swtempledata")
public class GbltConfigApplicationDataMst implements Serializable {

	@Id
	private Long unumApplicationId;

	@Id
	private Long unumApplicantId;

	@Id
	private Long unumNid;

	@Id
	private Long unumNdtlId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_application_date")
	private Date udtApplicationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_application_entry_date")
	private Date udtApplicationEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_application_submit_date")
	private Date udtApplicationSubmitDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_application_entry_status")
	private Integer unumApplicationEntryStatus;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_mtemplate_for")
	private Long unumMtemplateFor;

	@Column(name="unum_mtemplate_type")
	private Long unumMtemplateType;

	@Column(name="unum_n_dept_id")
	private Long unumNDeptId;

	@Column(name="unum_ndtl_course_id")
	private Long unumNdtlCourseId;

	@Column(name="unum_ndtl_department_id")
	private Long unumNdtlDepartmentId;

	@Column(name="unum_ndtl_faculty_id")
	private Long unumNdtlFacultyId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_application_description")
	private String ustrApplicationDescription;

	@Column(name="unum_ctype_id")
	private Integer unumCtypeId;

	@Column(name = "unum_mtemple_id")
	private Long unumMtempleId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_lst_mod_date")
	private Date udtLstModDate;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;
}