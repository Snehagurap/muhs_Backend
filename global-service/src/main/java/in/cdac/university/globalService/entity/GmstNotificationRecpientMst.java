package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstNotificationRecpientMstPK.class)
@Table(name="gmst_notification_recpient_mst", schema = "university")
public class GmstNotificationRecpientMst implements Serializable {

	@Id
	private Long unumNrecId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_frm")
	private Date udtEffFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_nrec_fname")
	private String ustrNrecFname;

	@Column(name="ustr_nrec_sname")
	private String ustrNrecSname;
}