package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_notification_type_mst", schema = "university")
@IdClass(GmstNotificationTypeMstPK.class)
public class GmstNotificationTypeMst implements Serializable {

	@Id
	private Integer unumNtypeId;

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

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_ntype_fname")
	private String ustrNtypeFname;

	@Column(name="ustr_ntype_sname")
	private String ustrNtypeSname;
}