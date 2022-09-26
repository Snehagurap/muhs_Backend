package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstSubjectMstPK.class)
@Table(name="gmst_subject_mst", schema = "university")
public class GmstSubjectMst implements Serializable {

	@Id
	private Long unumSubId;

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

	@Column(name="unum_exonn_subid")
	private Integer unumExonnSubid;

	@Column(name="unum_intranetsubid")
	private Integer unumIntranetsubid;

	@Column(name="unum_sub_category_id")
	private Integer unumSubCategoryId;

	@Column(name="unum_sub_duration_inmm")
	private Integer unumSubDurationInmm;

	@Column(name="unum_sub_duration_inyy")
	private Integer unumSubDurationInyy;

	@Column(name="unum_subtype_id")
	private Integer unumSubtypeId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_sub_code")
	private String ustrSubCode;

	@Column(name="ustr_sub_fname")
	private String ustrSubFname;

	@Column(name="ustr_sub_sname")
	private String ustrSubSname;

	@Column(name="ustr_teachers_sub_id")
	private String ustrTeachersSubId;

}