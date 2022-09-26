package in.cdac.university.globalService.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_course_type_mst", schema = "university")
public class GmstCourseTypeMst implements Serializable {

	@Id
	@Column(name="unum_ctype_id")
	private Long unumCtypeId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_ctype_min_eligibility_ctype_id")
	private Integer unumCtypeMinEligibilityCtypeId;

	@Column(name="unum_ctype_sequence")
	private Integer unumCtypeSequence;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_exon_clevelid")
	private Integer unumExonClevelid;

	@Column(name="unum_intra_ctypeid")
	private Integer unumIntraCtypeid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_ctype_code")
	private String ustrCtypeCode;

	@Column(name="ustr_ctype_description")
	private String ustrCtypeDescription;

	@Column(name="ustr_ctype_fname")
	private String ustrCtypeFname;

	@Column(name="ustr_ctype_min_eligibility")
	private String ustrCtypeMinEligibility;

	@Column(name="ustr_ctype_sname")
	private String ustrCtypeSname;

	@Column(name="ustr_description")
	private String ustrDescription;

}