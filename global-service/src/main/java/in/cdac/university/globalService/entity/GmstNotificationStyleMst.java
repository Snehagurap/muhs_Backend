package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstNotificationStyleMstPK.class)
@Entity
@Table(name="gmst_notification_style_mst", schema = "university")
public class GmstNotificationStyleMst implements Serializable {

	@Id
	private Long unumNstyleId;

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

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_nstyle_fname")
	private String ustrNstyleFname;

	@Column(name="ustr_nstyle_sname")
	private String ustrNstyleSname;
}