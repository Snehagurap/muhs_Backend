package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_faculty_mst", schema = "university")
@IdClass(GmstCoursefacultyMstPK.class)
public class GmstCoursefacultyMst implements Serializable {

	@Id
	private Integer unumCfacultyId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_exon_cfacultyid")
	private String unumExonCfacultyid;

	@Column(name="unum_intra_cfacultyid")
	private String unumIntraCfacultyid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_cfaculty_fname")
	private String ustrCfacultyFname;

	@Column(name="ustr_cfaculty_sname")
	private String ustrCfacultySname;

	@Column(name="ustr_description")
	private String ustrDescription;

}