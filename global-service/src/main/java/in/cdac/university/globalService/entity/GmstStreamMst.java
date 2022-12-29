package in.cdac.university.globalService.entity;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstStreamMstPK.class)
@Entity
@Table(name="gmst_stream_mst", schema = "university")
public class GmstStreamMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumStreamId;

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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_stream_code")
	private String ustrStreamCode;

	@Column(name="ustr_stream_fname")
	private String ustrStreamFname;

	@Column(name="ustr_stream_sname")
	private String ustrStreamSname;

}
