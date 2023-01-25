package in.cdac.university.globalService.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursePaternYearColStuDtl {
	
	private Long	unumCoursePatYearColStuId;
	
	private Long	unumCoursePatYearColId;
	
	private Date	udtDateOfAdmission;
	
	private Integer	unumAdmissionYear;
	
	private Long	unumCollegeId;
	
	private Long	unumCourseId;
	
	private Long	unumStudentId;
	
	private Long	unumResultStatusId;
	
	private Integer	unumResultStatusSno;
	
	private String	ustrResultGrade ;
	
	private Double	unumResultTotalMarks ;
	
	private Double	unumResultMarksObtained ;
	
	private String	ustrDescription ;
	
	private Date	udtEffFrom ;
	
	private Date	udtEffTo ;
	
	private Date	udtEntryDate ;
	
	private Integer	unumIsvalid ;
	
	private Integer	unumEntryUid ;
	
	private Integer	unumCoursePatId ;
	
	private Integer	unumLstModUid ;
	
	private Date	udtLstModDate ;
	
	private String	ustrPassingMarksheetPath;

}
