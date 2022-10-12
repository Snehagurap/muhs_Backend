package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(CmstCollegeCourseMstPK.class)
@Entity
@Table(name="cmst_college_course_mst", schema = "university")
public class CmstCollegeCourseMst implements Serializable {

	@Id
	private Long unumColCourseId;

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

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_college_id")
	private Long unumCollegeId;

	@Column(name="unum_course_id")
	private Long unumCourseId;

	@Column(name="unum_course_present_aff_status_id")
	private Integer unumCoursePresentAffStatusId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_course_present_affiliation_no")
	private String ustrCoursePresentAffiliationNo;

	@Column(name="ustr_description")
	private String ustrDescription;

}