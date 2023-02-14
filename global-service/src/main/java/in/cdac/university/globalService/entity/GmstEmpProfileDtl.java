package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstEmpProfileDtlPK.class)
@Table(name="gmst_emp_profile_dtl", schema = "university")
@ToString
public class GmstEmpProfileDtl implements Serializable {

	@Id
	private Long unumProfileId;

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

	@Column(name="unum_college_id")
	private Long unumCollegeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_faculty_id")
	private Integer unumFacultyId;

	@Column(name="unum_sub_id")
	private Long unumSubId;

	@Column(name="unum_course_id")
	private Long unumCourseId;

	@Column(name="unum_emp_id")
	private Long unumEmpId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name = "unum_stream_id")
	private Long unumStreamId;

	@Column(name = "unum_course_type_id")
	private Integer unumCourseTypeId;
}