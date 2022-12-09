package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstCourseMstPK.class)
@Table(name="gmst_course_mst", schema = "university")
public class GmstCourseMst implements Serializable {

	@Id
	private Long unumCourseId;

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

	@Column(name="unum_cd_category_id")
	private Integer unumCdCategoryId;

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_course_admissionmode")
	private Integer unumCourseAdmissionmode;

	@Column(name="unum_course_duration_inmm")
	private Integer unumCourseDurationInmm;

	@Column(name="unum_course_duration_inyy")
	private Integer unumCourseDurationInyy;

	@Column(name="ustr_course_lang_medium")
	private String unumCourseLangMedium;

	@Column(name="unum_course_maxmonths")
	private Integer unumCourseMaxmonths;

	@Column(name="unum_course_maxyears")
	private Integer unumCourseMaxyears;

	@Column(name="unum_course_minmonths")
	private Integer unumCourseMinmonths;

	@Column(name="unum_course_minyears")
	private Integer unumCourseMinyears;

	@Column(name="unum_exam_frequency")
	private Integer unumCoursePattern;

	@Column(name="unum_ctype_id")
	private Integer unumCtypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_exonn_cfcoid")
	private Integer unumExonnCfcoid;

	@Column(name="unum_exonn_levelid")
	private Integer unumExonnLevelid;

	@Column(name="unum_intranetcourseid")
	private Integer unumIntranetcourseid;

	@Column(name="unum_isacaddept")
	private Integer unumIsacaddept;

	@Column(name="unum_iscarryforward")
	private Integer unumIscarryforward;

	@Column(name="unum_iselegdept")
	private Integer unumIselegdept;

	@Column(name="unum_isexamdept")
	private Integer unumIsexamdept;

	@Column(name="unum_isscaledown")
	private Integer unumIsscaledown;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_course_code")
	private String ustrCourseCode;

	@Column(name="ustr_course_fname")
	private String ustrCourseFname;

	@Column(name="ustr_course_sname")
	private String ustrCourseSname;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_exonn_cocate")
	private String ustrExonnCocate;

	@Column(name="ustr_exonn_coursecd")
	private String ustrExonnCoursecd;

	@Column(name="ustr_exonn_facultycode")
	private String ustrExonnFacultycode;

	@Column(name="ustr_teachers_course_code")
	private String ustrTeachersCourseCode;

}