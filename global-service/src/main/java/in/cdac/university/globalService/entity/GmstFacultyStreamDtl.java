package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstFacultyStreamDtlPK.class)
@Table(name="gmst_faculty_stream_dtl", schema = "university")
public class GmstFacultyStreamDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumFacStreamId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date udtLstModDate;

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_fac_stream_code")
	private String ustrFacStreamCode;

	@Column(name="ustr_fac_stream_fname")
	private String ustrFacStreamFname;

	@Column(name="ustr_fac_stream_sname")
	private String ustrFacStreamSname;


}