package in.cdac.university.globalService.entity;

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
@Entity
@IdClass(GmstStreamMstPK.class)
@Table(name="gmst_stream_mst", schema = "university")
public class GmstStreamMst implements Serializable {
	
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
	
	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date udtLstModDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="ustr_stream_code")
	private String ustrStreamCode;
	
	@Column(name="ustr_stream_sname")
	private String ustrStreamSname;
	
	@Column(name="ustr_stream_fname")
	private String ustrStreamFname;
	
	@Column(name="ustr_description")
	private String ustrDescription;
	
	@Column(name="unum_univ_id")
	private Integer unumUnivId;
	
	@Column(name="unum_entry_uid")
	private Long unumEntryUid;
	
	@Column(name="unum_lst_mod_uid")
	private Integer unumLstModUid;
	
}
