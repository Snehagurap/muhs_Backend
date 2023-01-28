package in.cdac.university.globalService.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="gmst_course_patern_year_col_stu_dtl", schema = "university")
@IdClass(GmstCoursePaternYearColStuDtlPK.class)
public class GmstCoursePaternYearColStuDtl {
	
	@Id
	private Long	unumCoursePatYearColStuId;

	@Id
	private Integer	unumIsvalid ;

	@Column(name="unum_course_pat_year_col_id")
	private Long	unumCoursePatYearColId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_date_of_admission")
	private Date	udtDateOfAdmission;

	@Column(name="unum_admission_year")
	private Integer	unumAdmissionYear;

	@Column(name="unum_college_id")
	private Long	unumCollegeId;

	@Column(name="unum_course_id")
	private Long	unumCourseId;

	@Column(name="unum_student_id")
	private Long	unumStudentId;

	@Column(name="unum_result_status_id")
	private Long	unumResultStatusId;

	@Column(name="unum_result_status_sno")
	private Integer	unumResultStatusSno;

	@Column(name="ustr_result_grade")
	private String	ustrResultGrade ;

	@Column(name="unum_result_total_marks")
	private Double	unumResultTotalMarks ;

	@Column(name="unum_result_marks_obtained")
	private Double	unumResultMarksObtained ;

	@Column(name="ustr_description")
	private String	ustrDescription ;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date	udtEffFrom ;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date	udtEffTo ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date	udtEntryDate ;

	@Column(name="unum_entry_uid")
	private Integer	unumEntryUid ;

	@Column(name="unum_course_pat_id")
	private Integer	unumCoursePatId ;

	@Column(name="unum_lst_mod_uid")
	private Integer	unumLstModUid ;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date	udtLstModDate ;

	@Column(name="ustr_passing_marksheet_path")
	private String	ustrPassingMarksheetPath;
}
