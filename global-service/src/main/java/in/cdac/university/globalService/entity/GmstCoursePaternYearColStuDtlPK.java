package in.cdac.university.globalService.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class GmstCoursePaternYearColStuDtlPK implements Serializable {
	
	@Column(name="unum_course_pat_year_col_stu_id")
	private Long	unumCoursePatYearColStuId;

	@Column(name="unum_isvalid")
	private Integer	unumIsvalid ;

}
