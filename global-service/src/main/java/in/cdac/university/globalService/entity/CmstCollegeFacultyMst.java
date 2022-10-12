package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(CmstCollegeFacultyMstPK.class)
@Table(name="cmst_college_faculty_mst", schema = "university")
public class CmstCollegeFacultyMst implements Serializable {

	@Id
	private Long unumColFacId;

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

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;
}