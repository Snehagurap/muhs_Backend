package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltConfigApplicationHtmlheaderMstPK.class)
@Entity
@Table(name="gblt_config_application_htmlheader_mst", schema = "templedata")
public class GbltConfigApplicationHtmlheaderMst implements Serializable {

	@Id
	private Long unumApplicationId;

	@Id
	private Long unumApplicantId;

	@Id
	private Long unumNid;

	@Id
	private Long unumHtmlheaderId;

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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_header_display_order")
	private Integer unumHeaderDisplayOrder;

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_mtempledtl_id")
	private Long unumMtempledtlId;

	@Column(name="unum_ndtl_course_id")
	private Long unumNdtlCourseId;

	@Column(name="unum_ndtl_department_id")
	private Long unumNdtlDepartmentId;

	@Column(name="unum_ndtl_faculty_id")
	private Long unumNdtlFacultyId;

	@Column(name="unum_ndtl_id")
	private Long unumNdtlId;

	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_htmlheader_description")
	private String ustrHtmlheaderDescription;

	@Column(name="ustr_htmlheader_value")
	private String ustrHtmlheaderValue;

}